package kr.neoventureholdings.realword_backend.auth.domains;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
  private String email;
  private String username;
  private String password;
  private String image;
  private String bio;
}
