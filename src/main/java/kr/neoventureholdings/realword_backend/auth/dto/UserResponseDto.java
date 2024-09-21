package kr.neoventureholdings.realword_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import kr.neoventureholdings.realword_backend.auth.domains.User;
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
  private String email;
  private String username;
  private String image;
  private String bio;
  @JsonInclude(Include.NON_EMPTY)
  private String token;

  public static UserResponseDto of(User user) {
    return UserResponseDto.builder()
        .email(user.getEmail())
        .username(user.getProfile().getUsername())
        .image(user.getProfile().getImage())
        .bio(user.getProfile().getBio())
        .build();
  }
}
