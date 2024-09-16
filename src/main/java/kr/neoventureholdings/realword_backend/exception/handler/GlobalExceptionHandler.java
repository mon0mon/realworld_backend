package kr.neoventureholdings.realword_backend.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import java.util.List;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import kr.neoventureholdings.realword_backend.exception.auth.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException e) {
    log.error("ValidationException", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
        e.getBindingResult());
    return new ResponseEntity<>(response, response.getStatus());
  }

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
    log.error("Auth Exception", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST,
        List.of(e.getMessage()));
    return new ResponseEntity<>(response, response.getStatus());
  }

  @ExceptionHandler(JWTDecodeException.class)
  public ResponseEntity<ErrorResponse> handleJwtTokenException(JWTDecodeException e) {
    log.error("JWT Token Decode Exception", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, List.of("Token Authentication Failed"));
    return new ResponseEntity<>(response, response.getStatus());
  }
}
