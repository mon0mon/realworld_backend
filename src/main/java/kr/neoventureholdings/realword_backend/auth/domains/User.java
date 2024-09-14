package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
  @Id
  private Long id;
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

  public UserResponseDto userResponseDto() {
    return UserResponseDto.builder()
        .username(username)
        .email(email)
        .bio(bio)
        .image(image)
        .build();
  }
}
