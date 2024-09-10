package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Profile {
  @NotNull
  private User user;
  private boolean isFollowing;
}
