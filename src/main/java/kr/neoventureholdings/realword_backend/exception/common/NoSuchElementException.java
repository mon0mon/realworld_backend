package kr.neoventureholdings.realword_backend.exception.common;

public class NoSuchElementException extends CommonException{

  public NoSuchElementException() {
    super(CommonExceptionType.NO_SUCH_ELEMENT);
  }

  public NoSuchElementException(String message) {
    super(message, CommonExceptionType.NO_SUCH_ELEMENT);
  }

  public NoSuchElementException(String message, Throwable cause) {
    super(message, cause, CommonExceptionType.NO_SUCH_ELEMENT);
  }

  public NoSuchElementException(Throwable cause) {
    super(cause, CommonExceptionType.NO_SUCH_ELEMENT);
  }

  public NoSuchElementException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, CommonExceptionType.NO_SUCH_ELEMENT);
  }
}
