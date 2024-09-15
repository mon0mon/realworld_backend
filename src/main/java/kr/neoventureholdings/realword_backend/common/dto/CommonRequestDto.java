package kr.neoventureholdings.realword_backend.common.dto;

import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequestDto {
  private UserRequestDto user;
}
