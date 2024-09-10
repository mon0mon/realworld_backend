package kr.neoventureholdings.realword_backend.auth.domains;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Profile {
  private User user;
  private boolean isFollowing;
}
