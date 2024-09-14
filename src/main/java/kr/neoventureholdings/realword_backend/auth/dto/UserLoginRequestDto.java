package kr.neoventureholdings.realword_backend.auth.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
  private String username;
  private String password;
}
