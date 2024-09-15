package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Email
  @NotEmpty
  @Column
  private String email;
  @NotEmpty
  @Column
  private String username;
  @NotEmpty
  @Column
  private String password;
  @Column
  private String image;
  @Column
  private String bio;

  public UserResponseDto userResponseDto() {
    return UserResponseDto.builder()
        .username(username)
        .email(email)
        .bio(bio)
        .image(image)
        .build();
  }

  public UserResponseDto userResponseDto(String token) {
    return UserResponseDto.builder()
        .username(username)
        .email(email)
        .bio(bio)
        .image(image)
        .token(token)
        .build();
  }
}
