package kr.neoventureholdings.realword_backend.common.dto;

import jakarta.validation.Valid;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequestDto {
  @Valid
  private UserRequestDto user;
}
