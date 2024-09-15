package kr.neoventureholdings.realword_backend.util;

import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieUtil {

  private static final String ACCESS_TOKEN = "token";
  @Value("${jwt.accessTokenExpire}")
  private String accessTokenExpire;

  @Value("${jwt.refreshTokenExpire}")
  private String refreshTokenExpire;

  public ResponseCookie setCookie(String name, String value, int maxAge) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(maxAge)
        .domain("localhost")
        .build();
  }

  public ResponseCookie setAccessToken(AccessTokenResponseDto accessTokenResponseDto) {
    return setCookie(ACCESS_TOKEN, accessTokenResponseDto.getToken(),
        Integer.parseInt(accessTokenExpire));
  }
}
