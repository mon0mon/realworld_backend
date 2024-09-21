package kr.neoventureholdings.realword_backend.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.neoventureholdings.realword_backend.common.dto.ErrorResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetailsService;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider.JWTInfo;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenValidationState;
import kr.neoventureholdings.realword_backend.constant.TokenConstant;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import kr.neoventureholdings.realword_backend.util.ErrorResponseHandler;
import kr.neoventureholdings.realword_backend.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
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

  private final List<RequestMatcher> excludeUrlMatchers = List.of(
      new AntPathRequestMatcher("/h2-console/**"),
      new AntPathRequestMatcher("/users/**"),
      new AntPathRequestMatcher("/tags")
  );

  private final List<RequestMatcher> authOptionalUrlMatchers = List.of(
      new AntPathRequestMatcher("/profiles/*", HttpMethod.GET.name()),
      new AntPathRequestMatcher("/articles", HttpMethod.GET.name()),
      new AntPathRequestMatcher("/articles/*", HttpMethod.GET.name()),
      new AntPathRequestMatcher("/articles/*/comments", HttpMethod.GET.name())
  );

  private final List<RequestMatcher> authRequiredUrlMatchers = List.of(
      new AntPathRequestMatcher("/articles/feed", HttpMethod.GET.name())
  );

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return excludeUrlMatchers
        .stream()
        .anyMatch(matcher ->
            matcher.matches(request)
        );
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith(
        TokenConstant.TOKEN_HEADER_PREFIX)) {
      token = StringUtil.getToken(authorizationHeader);
    }

    boolean isAuthOptional = authOptionalUrlMatchers
        .stream()
        .anyMatch(matcher
            -> matcher.matches(request)
        );

    //  wildcard로 지정된 URL에 경우 하위 URL에 대해서 인증처리를 요구할 때 지정
    //  true : wildcard로 지정된 authOptionalUrlMatchers 중에 현재 URL이 인증처리를 요구
    //  ex) /articles/* /articles/feed
    //  false : 현재 URL은 wildcard에 해당하지 않음
    boolean isWildcardUrlThatRequireAuth = authRequiredUrlMatchers
        .stream()
        .anyMatch(matcher -> matcher.matches(request));

    if (!StringUtils.hasText(token)) {
      if (isAuthOptional && !isWildcardUrlThatRequireAuth) {
        log.debug("[Security] : 인증정보 Optional ({})", request.getRequestURI());
        CustomUserDetail anonymousUserDetail = new CustomUserDetail();
        anonymousUserDetail.setAnonymous(true);
        AnonymousAuthenticationToken anonymousToken = new AnonymousAuthenticationToken(
            "anonymousUser", anonymousUserDetail,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        SecurityContextHolder.getContext().setAuthentication(anonymousToken);
        filterChain.doFilter(request, response);
        return;
      }

      log.info("[Security] : 유효한 JWT토큰이 없습니다.");

      ErrorResponseHandler.handleErrorResponse(response, ErrorResponseDto.builder()
          .status(HttpStatus.UNAUTHORIZED)
          .body(List.of("request requires authentication"))
          .build()
      );
      return;
    }

    //  주어진 토큰 유효성 검증
    JwtTokenValidationState tokenValidationState = jwtTokenProvider.validateToken(token);
    switch (tokenValidationState) {
      case SUCCESS -> log.debug("token validation success");
      default -> {
        log.debug("token validation failed");
        ErrorResponseHandler.handleErrorResponse(response, ErrorResponseDto.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .body(List.of(tokenValidationState.getValue()))
            .build());
        return;
      }
    }

    JWTInfo jwtInfo = jwtTokenProvider.decodeToken(token);

    String parsedToken = token.replace("token ", "");
    CustomUserDetail userDetail = userDetailsService.loadUserById(jwtInfo.getUserId());
    userDetail.setToken(parsedToken);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetail, null, userDetail.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    if (jwtInfo.getIsAccessToken()) {
      request.setAttribute(TokenConstant.ACCESS_TOKEN, parsedToken);
    }

    filterChain.doFilter(request, response);
  }
}