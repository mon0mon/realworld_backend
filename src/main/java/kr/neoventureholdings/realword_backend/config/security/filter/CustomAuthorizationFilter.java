package kr.neoventureholdings.realword_backend.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetailsService;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider.JWTInfo;
import kr.neoventureholdings.realword_backend.constant.TokenConstant;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  private final List<String> EXCLUDE_URL_LIST =
      List.of(
          "/h2-console",
          "/users"
      );

  private final HashSet<String> EXCLUDE_URL = new HashSet<>(EXCLUDE_URL_LIST);

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return EXCLUDE_URL
        .stream()
        .anyMatch(url ->
            request.getRequestURI().startsWith(url)
        );
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith(
        TokenConstant.TOKEN_HEADER_PREFIX)) {
      token = getToken(authorizationHeader);
    }

    //  인증 정보가 없을 경우 401 Unauthorized 리턴
    //  https://www.realworld.how/specifications/backend/error-handling/
    if (!StringUtils.hasText(token) || !jwtTokenProvider.validateToken(token)) {
      log.info("[Security] : 유효한 JWT토큰이 없습니다.");

      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,
          List.of("request requires authentication"));
      response.setStatus(errorResponse.getStatus().value());
      response.setContentType(APPLICATION_JSON_VALUE);
      objectMapper.writeValue(response.getOutputStream(), errorResponse);
      filterChain.doFilter(request, response);
    }

    JWTInfo jwtInfo = null;
    jwtInfo = jwtTokenProvider.decodeToken(token);

    CustomUserDetail userDetail = userDetailsService.loadUserById(jwtInfo.getUserId());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetail, null, userDetail.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    assert token != null;
    if (jwtInfo.getIsAccessToken()) {
      request.setAttribute(TokenConstant.ACCESS_TOKEN, token.replace("token ", ""));
    }

    filterChain.doFilter(request, response);
  }

  private String getToken(String tokenHeader) {
    return tokenHeader.substring(TokenConstant.TOKEN_HEADER_PREFIX.length());
  }
}
