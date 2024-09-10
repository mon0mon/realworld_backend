package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
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
