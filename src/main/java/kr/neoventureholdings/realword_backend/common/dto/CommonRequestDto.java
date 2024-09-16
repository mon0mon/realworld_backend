package kr.neoventureholdings.realword_backend.common.dto;

import jakarta.validation.Valid;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonRequestDto {
  @Valid
  private UserRequestDto user;
}
