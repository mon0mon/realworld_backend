package kr.neoventureholdings.realword_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDto {
  @Email
  @NotEmpty
  private String email;
  @Size(min = 5, max = 20)
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;

  public User toUser(PasswordEncoder passwordEncoder) {
    return User.builder()
        .username(this.username)
        .password(passwordEncoder.encode(this.password))
        .email(this.email)
        .build();
  }
}
