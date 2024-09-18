package kr.neoventureholdings.realword_backend.exception.auth;

public class NoAuthenticationException extends AuthException{

  public NoAuthenticationException() {
    super(AuthExceptionType.NO_AUTHENTICATION);
  }

  public NoAuthenticationException(String message) {
    super(message, AuthExceptionType.NO_AUTHENTICATION);
  }

  public NoAuthenticationException(String message, Throwable cause) {
    super(message, cause, AuthExceptionType.NO_AUTHENTICATION);
  }

  public NoAuthenticationException(Throwable cause) {
    super(cause, AuthExceptionType.NO_AUTHENTICATION);
  }

  public NoAuthenticationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, AuthExceptionType.NO_AUTHENTICATION);
  }
}
