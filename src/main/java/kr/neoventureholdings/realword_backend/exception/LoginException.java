package kr.neoventureholdings.realword_backend.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

  public LoginException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public LoginException(String msg) {
    super(msg);
  }
}
