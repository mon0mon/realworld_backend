package kr.neoventureholdings.realword_backend.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.SignatureException;
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

  private static final String ACCESS_TOKEN = "access_token";
  private static final String REFRESH_TOKEN = "refresh_token";
  private final JwtConfig jwtConfig;

  public AccessTokenResponseDto createAccessToken(Long userId) {
    Instant expiresAt = Instant.now().plusSeconds(Long.parseLong(jwtConfig.getAccessTokenExpire()));
    String accessToken = JWT.create()
        .withClaim("user", Map.of("id", userId))
        .withSubject(ACCESS_TOKEN)
        .withIssuer("RealWorld")
        .withIssuedAt(Date.from(Instant.now()))
        .withExpiresAt(Date.from(expiresAt))
        .sign(jwtConfig.getEncodedSecretKey());
    return new AccessTokenResponseDto(accessToken);
  }

  public AccessTokenResponseDto createRefreshToken(Long userId) {
    Instant expiresAt = Instant.now()
        .plusSeconds(Long.parseLong(jwtConfig.getRefreshTokenExpire()));
    String refreshToken = JWT.create()
        .withClaim("user", Map.of("id", userId))
        .withSubject(REFRESH_TOKEN)
        .withIssuer("RealWorld")
        .withIssuedAt(Date.from(Instant.now()))
        .withExpiresAt(Date.from(expiresAt))
        .sign(jwtConfig.getEncodedSecretKey());
    return new AccessTokenResponseDto(refreshToken);
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

    Map<String, Object> userClaim = decodedJWT.getClaim("user").asMap();
    Long userId = ((Number) userClaim.get("id")).longValue();
    Boolean isAccessToken = decodedJWT.getSubject().equals(ACCESS_TOKEN);

    return JWTInfo.builder()
        .userId(userId)
        .isAccessToken(isAccessToken)
        .build();
  }

  public JWTInfo decodeRefreshToken(String refreshToken) {
    JWTVerifier verifier = JWT.require(jwtConfig.getEncodedSecretKey()).build();

    DecodedJWT decodedJWT = verifier.verify(refreshToken);

    Map<String, Object> userClaim = decodedJWT.getClaim("user").asMap();
    Long userId = ((Number) userClaim.get("id")).longValue();
    Boolean isAccessToken = decodedJWT.getSubject().equals(ACCESS_TOKEN);

    return JWTInfo.builder()
        .userId(userId)
        .isAccessToken(isAccessToken)
        .build();
  }

  public boolean isCookieNameRefreshToken(Cookie cookie) {
    return JwtTokenProvider.REFRESH_TOKEN.equals(cookie.getName());
  }

  public JwtTokenValidationState validateToken(String token) {
    try {
      JWT.require(jwtConfig.getEncodedSecretKey()).build().verify(token);
      return JwtTokenValidationState.SUCCESS;
    } catch (AlgorithmMismatchException e) {
      log.info("JWT Algorithm Mismatch");
      return JwtTokenValidationState.TOKEN_ALGORITHM_MISMATCH;
    } catch (SignatureException e) {
      log.info("JWT Signature Not valid");
      return JwtTokenValidationState.TOKEN_SIGNATURE_NOT_VALID;
    } catch (TokenExpiredException e) { // 만료된 JWT
      log.info("Expired JWT");
      return JwtTokenValidationState.TOKEN_EXPIRED;
    } catch (InvalidClaimException e) { // 지원하지 않는 JWT
      log.info("Invalid JWT Claim");
      return JwtTokenValidationState.TOKEN_NOT_SUPPORTED;
    } catch (JWTVerificationException e) {
      log.info("JWT 인증 오류");
      return JwtTokenValidationState.TOKEN_VERIFICATION_ERROR;
    }
  }

  @Getter
  @Builder
  @ToString
  public static class JWTInfo {

    private final Long userId;
    private final Boolean isAccessToken;
  }
}
