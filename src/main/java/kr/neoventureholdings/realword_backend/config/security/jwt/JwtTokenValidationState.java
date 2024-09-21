package kr.neoventureholdings.realword_backend.config.security.jwt;

import lombok.Getter;

@Getter
public enum JwtTokenValidationState {
  SUCCESS("success"),
  TOKEN_SIGNATURE_NOT_VALID("token signature not valid"),
  TOKEN_EXPIRED("token expired"),
  TOKEN_NOT_SUPPORTED("token not supported"),
  TOKEN_VERIFICATION_ERROR("token verification failed"),
  TOKEN_ALGORITHM_MISMATCH("token algorithm mismatch");

  private String value;

  JwtTokenValidationState(String value) {
    this.value = value;
  }
}
