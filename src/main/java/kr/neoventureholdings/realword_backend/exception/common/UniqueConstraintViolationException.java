package kr.neoventureholdings.realword_backend.exception.common;

public class UniqueConstraintViolationException extends CommonException{

  public UniqueConstraintViolationException() {
    super(CommonExceptionType.UNIQUE_CONSTRAINT_VIOLATION);
  }

  public UniqueConstraintViolationException(String message) {
    super(message, CommonExceptionType.UNIQUE_CONSTRAINT_VIOLATION);
  }

  public UniqueConstraintViolationException(String message, Throwable cause) {
    super(message, cause, CommonExceptionType.UNIQUE_CONSTRAINT_VIOLATION);
  }

  public UniqueConstraintViolationException(Throwable cause) {
    super(cause, CommonExceptionType.UNIQUE_CONSTRAINT_VIOLATION);
  }

  public UniqueConstraintViolationException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, CommonExceptionType.UNIQUE_CONSTRAINT_VIOLATION);
  }
}
