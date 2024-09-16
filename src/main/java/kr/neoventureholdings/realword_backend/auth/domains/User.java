package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
  @Column(unique = true)
  private String email;
  @NotEmpty
  @Column(unique = true)
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
        .username(getUsername())
        .email(getEmail())
        .bio(getBio())
        .image(getImage())
        .build();
  }

  public UserResponseDto userResponseDto(String token) {
    return UserResponseDto.builder()
        .username(getUsername())
        .email(getEmail())
        .bio(getBio())
        .image(getImage())
        .token(token)
        .build();
  }

  public User of(UserRequestDto requestDto) {
    return User.builder()
        .id(getId())
        .username(StringUtils.hasText(requestDto.getUsername()) ? requestDto.getUsername() : getUsername())
        .email(StringUtils.hasText(requestDto.getEmail()) ? requestDto.getEmail() : getEmail())
        .password(StringUtils.hasText(requestDto.getPassword()) ? requestDto.getPassword() : getPassword())
        .bio(StringUtils.hasText(requestDto.getBio()) ? requestDto.getBio() : getBio())
        .image(StringUtils.hasText(requestDto.getImage()) ? requestDto.getImage() : getImage())
        .build();
  }
}
