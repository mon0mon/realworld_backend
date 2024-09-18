package kr.neoventureholdings.realword_backend.exception.common;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
  private final CommonExceptionType type;

  public CommonException(CommonExceptionType type) {
    this.type = type;
  }

  public CommonException(String message, CommonExceptionType type) {
    super(message);
    this.type = type;
  }

  public CommonException(String message, Throwable cause, CommonExceptionType type) {
    super(message, cause);
    this.type = type;
  }

  public CommonException(Throwable cause, CommonExceptionType type) {
    super(cause);
    this.type = type;
  }

  public CommonException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, CommonExceptionType type) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.type = type;
  }
}
