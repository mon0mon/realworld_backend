package kr.neoventureholdings.realword_backend.exception.auth;

public class UserLoginException extends AuthException {

  public UserLoginException() {
    super(AuthExceptionType.LOGIN_USER);
  }

  public UserLoginException(String message) {
    super(message, AuthExceptionType.LOGIN_USER);
  }

  public UserLoginException(String message, Throwable cause) {
    super(message, cause, AuthExceptionType.LOGIN_USER);
  }

  public UserLoginException(Throwable cause) {
    super(cause, AuthExceptionType.LOGIN_USER);
  }

  public UserLoginException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, AuthExceptionType.LOGIN_USER);
  }
}
