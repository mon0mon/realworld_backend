package kr.neoventureholdings.realword_backend.exception.auth;

public class UserRegisterExcpetion extends AuthException {

  public UserRegisterExcpetion() {
    super(AuthExceptionType.REGISTER_USER);
  }

  public UserRegisterExcpetion(String message) {
    super(message, AuthExceptionType.REGISTER_USER);
  }

  public UserRegisterExcpetion(String message, Throwable cause) {
    super(message, cause, AuthExceptionType.REGISTER_USER);
  }

  public UserRegisterExcpetion(Throwable cause) {
    super(cause, AuthExceptionType.REGISTER_USER);
  }

  public UserRegisterExcpetion(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, AuthExceptionType.REGISTER_USER);
  }
}
