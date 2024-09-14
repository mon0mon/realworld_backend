package kr.neoventureholdings.realword_backend.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private int status;
  private List<FieldError> errors;

  private ErrorResponse(final HttpStatus statusCode, final List<FieldError> errors) {
    this.status = statusCode.value();
    this.errors = errors;
  }

  public static ErrorResponse of(final HttpStatus statusCode, final BindingResult bindingResult) {
    return new ErrorResponse(statusCode, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(final HttpStatus statusCode, final List<FieldError> errors) {
    return new ErrorResponse(statusCode, errors);
  }

  @Getter
  @NoArgsConstructor
  public static class FieldError {

    private String reason;

    private FieldError(final String reason) {
      this.reason = reason;
    }

    public static List<FieldError> of(final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(error.getDefaultMessage()))
          .toList();
    }
  }
}
