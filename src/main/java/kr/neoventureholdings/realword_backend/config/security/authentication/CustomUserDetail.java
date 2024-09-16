package kr.neoventureholdings.realword_backend.config.security.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
public class CustomUserDetail implements UserDetails, Serializable {

  private Long id;
  private String email;
  private String username;
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

  public static CustomUserDetail of(User user) {
    return CustomUserDetail.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .email(user.getEmail())
        .id(user.getId())
        .build();
  }
}
