package kr.neoventureholdings.realword_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
  @Email
  @NotEmpty
  private String email;
  @Size(min = 5, max = 20)
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  private String image;
  private String bio;
}
