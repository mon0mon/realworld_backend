package kr.neoventureholdings.realword_backend.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

  private static final String Token = "Token ";
  private static final String ACCESS_TOKEN = "access_token";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String EMAIL = "email";
  private static final String USERNAME = "username";
  private final JwtConfig jwtConfig;

  public AccessTokenResponseDto createAccessToken(String username) {
    Instant expiresAt = Instant.now().plusSeconds(Long.parseLong(jwtConfig.getAccessTokenExpire()));
    String accessToken = Token + JWT.create()
        .withSubject(ACCESS_TOKEN)
        .withIssuer("RealWorld")
        .withIssuedAt(Date.from(Instant.now()))
        .withExpiresAt(Date.from(expiresAt))
        .withClaim(USERNAME, username)
        .sign(jwtConfig.getEncodedSecretKey());
    return new AccessTokenResponseDto(accessToken, expiresAt.getNano());
  }

  public AccessTokenResponseDto createRefreshToken(String username) {
    Instant expiresAt = Instant.now()
        .plusSeconds(Long.parseLong(jwtConfig.getRefreshTokenExpire()));
    String refreshToken = JWT.create()
        .withSubject(REFRESH_TOKEN)
        .withIssuer("RealWorld")
        .withIssuedAt(Date.from(Instant.now()))
        .withExpiresAt(Date.from(expiresAt))
        .withClaim(USERNAME, username)
        .sign(jwtConfig.getEncodedSecretKey());
    return new AccessTokenResponseDto(refreshToken, expiresAt.getNano());
  }

  /**
   * 1. 토큰이 정상적인지 검증(위조, 만료 여부) 2. Access Token인지 Refresh Token인지 구분
   *
   * @param token
   * @return
   * @throws JWTVerificationException
   */
  public JWTInfo decodeToken(String token)
      throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(jwtConfig.getEncodedSecretKey()).build();

    DecodedJWT decodedJWT = verifier.verify(token);

    Boolean isAccessToken = decodedJWT.getSubject().equals(ACCESS_TOKEN);

    return JWTInfo.builder()
        .username(decodedJWT.getClaim(USERNAME).asString())
        .isAccessToken(isAccessToken)
        .build();
  }

  public JWTInfo decodeRefreshToken(String refreshToken) {
    JWTVerifier verifier = JWT.require(jwtConfig.getEncodedSecretKey()).build();

    DecodedJWT decodedJWT = verifier.verify(refreshToken);

    return JWTInfo.builder()
        .username(decodedJWT.getClaim(USERNAME).asString())
        .build();
  }

  public boolean isCookieNameRefreshToken(Cookie cookie) {
    return JwtTokenProvider.REFRESH_TOKEN.equals(cookie.getName());
  }

  public boolean validateToken(String token) {
    try {
      JWT.require(jwtConfig.getEncodedSecretKey()).build().verify(token);
      return true;
    } catch (SignatureException e) { // 유효하지 않은 JWT 서명
      log.info("not valid jwt signature");
    } catch (MalformedJwtException e) { // 유효하지 않은 JWT
      log.info("not valid jwt");
    } catch (ExpiredJwtException e) { // 만료된 JWT
      log.info("expired jwt");
    } catch (UnsupportedJwtException e) { // 지원하지 않는 JWT
      log.info("unsupported jwt");
    } catch (IllegalArgumentException e) { // 빈값
      log.info("empty jwt");
    }
    return false;
  }

  @Getter
  @Builder
  @ToString
  public static class JWTInfo {

    private final String username;
    private final Boolean isAccessToken;
  }
}
