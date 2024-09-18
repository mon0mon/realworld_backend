package kr.neoventureholdings.realword_backend.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  @JsonIgnore
  private HttpStatus status;
  private List<String> body = new ArrayList<>();

  public ErrorResponse(HttpStatus httpStatus) {
    this.status = httpStatus;
  }

  public ErrorResponse(HttpStatus httpStatus, BindingResult bindingResult) {
    this.status = httpStatus;
    this.body = extractBindingResults(bindingResult);
  }

  public void setBody(String message) {
    this.body = new ArrayList<>(List.of(message));
  }

  public void setBody(List<String> body) {
    this.body = body;
  }

  public void addBody(String message) {
    this.body.add(message);
  }

  public void addBody(List<String> body) {
    this.body.addAll(body);
  }

  public void setBody(BindingResult bindingResult) {
    this.body = extractBindingResults(bindingResult);
  }

  public void addBody(BindingResult bindingResult) {
    this.body.addAll(extractBindingResults(bindingResult)
    );
  }

  private static List<String> extractBindingResults(BindingResult bindingResult) {
    return bindingResult.getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
        .toList();
  }
}