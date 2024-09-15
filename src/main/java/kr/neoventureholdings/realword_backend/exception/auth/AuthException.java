package kr.neoventureholdings.realword_backend.exception.auth;


public class AuthException extends RuntimeException {
  private final AuthExceptionType type;

  public AuthException(AuthExceptionType type) {
    super();
    this.type = type;
  }

  public AuthException(String message, AuthExceptionType type) {
    super(message);
    this.type = type;
  }

  public AuthException(String message, Throwable cause, AuthExceptionType type) {
    super(message, cause);
    this.type = type;
  }

  public AuthException(Throwable cause, AuthExceptionType type) {
    super(cause);
    this.type = type;
  }

  public AuthException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, AuthExceptionType type) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.type = type;
  }
}
