package kr.neoventureholdings.realword_backend.exception.common;

public class EntityAlreadyExistsException extends CommonException {

  public EntityAlreadyExistsException() {
    super(CommonExceptionType.ENTITY_ALREADY_EXISTS);
  }

  public EntityAlreadyExistsException(String message) {
    super(message, CommonExceptionType.ENTITY_ALREADY_EXISTS);
  }

  public EntityAlreadyExistsException(String message, Throwable cause) {
    super(message, cause, CommonExceptionType.ENTITY_ALREADY_EXISTS);
  }

  public EntityAlreadyExistsException(Throwable cause) {
    super(cause, CommonExceptionType.ENTITY_ALREADY_EXISTS);
  }

  public EntityAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace, CommonExceptionType.ENTITY_ALREADY_EXISTS);
  }
}
