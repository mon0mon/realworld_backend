package kr.neoventureholdings.realword_backend.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.config.security.JwtTokenProvider.JWTInfo;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
//@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private static final String TOKEN = "Token ";

  private final List<String> EXCLUDE_URL_LIST =
      List.of(
          "/users",
          "/users/login"
      );

  private final HashSet<String> EXCLUDE_URL = new HashSet<>(EXCLUDE_URL_LIST);

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    log.info("필터 타는지 확인 여부 : {}", request.getRequestURL());
    return EXCLUDE_URL.contains(request.getRequestURI());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN)) {
      token = getToken(authorizationHeader);
    }

    JWTInfo jwtInfo = null;
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
      jwtInfo = jwtTokenProvider.decodeToken(token);
      SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(jwtInfo));
    } else {
      log.info("유효한 JWT토큰이 없습니다.");
    }

    filterChain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(JWTInfo jwtInfo) {
    return new UsernamePasswordAuthenticationToken(
        CustomUserDetail.of(
            User.builder()
                .username(jwtInfo.getUsername())
                .build()
        ),
        null
    );
  }

  private static void getAccessTokenExpired(HttpServletResponse response,
      ErrorResponse errorResponse) throws IOException {
    response.setStatus(errorResponse.getStatus());
    Map<String, String> body = new HashMap<>();
    body.put("status", Integer.toString(errorResponse.getStatus()));
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), body);
  }

  private String getToken(String tokenHeader) {
    return tokenHeader.substring(TOKEN.length());
  }

}
