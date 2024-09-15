package kr.neoventureholdings.realword_backend.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class CommonResponseDto {
  @JsonProperty("user")
  private UserResponseDto userResponseDto;
}
