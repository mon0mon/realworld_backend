package kr.neoventureholdings.realword_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserRequestDto {
  @Email(groups = {Registration.class, Login.class})
  @NotBlank(groups = {Registration.class, Login.class})
  private String email;
  @NotBlank(groups = {Registration.class})
  private String username;
  @NotBlank(groups = {Registration.class, Login.class})
  private String password;
  private String image;
  private String bio;

  public User toUser(PasswordEncoder passwordEncoder) {
    return User.builder()
        .username(this.username)
        .password(passwordEncoder.encode(this.password))
        .email(this.email)
        .image(image)
        .bio(bio)
        .build();
  }

  //  Validation Groups
  public interface Registration {}
  public interface Login {}
  public interface Update {}
}
