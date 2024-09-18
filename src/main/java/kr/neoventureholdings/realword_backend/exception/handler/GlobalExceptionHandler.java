package kr.neoventureholdings.realword_backend.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import java.sql.SQLException;
import java.util.List;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import kr.neoventureholdings.realword_backend.exception.auth.AuthException;
import kr.neoventureholdings.realword_backend.exception.common.CommonException;
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

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorResponse> handleNoAuthorizationException(CommonException e) {
    log.error("CommonException : ", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
        List.of(e.getMessage()));

    switch (e.getType()) {
      case NO_SUCH_ELEMENT ->
        response.setStatus(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(response, response.getStatus());
  }

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
    log.error("Auth Exception", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST,
        List.of(e.getMessage()));

    switch (e.getType()) {
      case NO_AUTHORIZATION ->
        response.setStatus(HttpStatus.FORBIDDEN);
      case NO_AUTHENTICATION ->
        response.setStatus(HttpStatus.UNAUTHORIZED);
    }

    return new ResponseEntity<>(response, response.getStatus());
  }

  @ExceptionHandler(JWTDecodeException.class)
  public ResponseEntity<ErrorResponse> handleJwtTokenException(JWTDecodeException e) {
    log.error("JWT Token Decode Exception", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, List.of("Token Authentication Failed"));
    return new ResponseEntity<>(response, response.getStatus());
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ErrorResponse> handleSqlException(SQLException e) {
    log.error("SQL Exception", e);
    ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
        List.of("Failed to Process SQL"));
    return new ResponseEntity<>(response, response.getStatus());
  }
}
