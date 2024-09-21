package kr.neoventureholdings.realword_backend.util;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.neoventureholdings.realword_backend.common.dto.ErrorResponseDto;
import kr.neoventureholdings.realword_backend.exception.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseHandler {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static ErrorResponse buildErrorResponse(ErrorResponseDto dto) {
    return new ErrorResponse(dto.getStatus(), dto.getBody());
  }

  public static void handleErrorResponse(HttpServletResponse response, ErrorResponseDto dto)
      throws IOException {
    response.setStatus(dto.getStatus().value());
    response.setContentType(APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getOutputStream(), buildErrorResponse(dto));
  }
}
