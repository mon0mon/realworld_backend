package kr.neoventureholdings.realword_backend.exception.auth;

public class NoAuthorizationException  extends AuthException{

  public NoAuthorizationException() {
    super(AuthExceptionType.NO_AUTHORIZATION);
  }

  public NoAuthorizationException(String message) {
    super(message, AuthExceptionType.NO_AUTHORIZATION);
  }

  public NoAuthorizationException(String message, Throwable cause) {
    super(message, cause, AuthExceptionType.NO_AUTHORIZATION);
  }

  public NoAuthorizationException(Throwable cause) {
    super(cause, AuthExceptionType.NO_AUTHORIZATION);
  }

  public NoAuthorizationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, AuthExceptionType.NO_AUTHORIZATION);
  }
}
