package kr.neoventureholdings.realword_backend.config.security.authentication;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication instanceof AnonymousAuthenticationToken) {
      CustomUserDetail anonymousUserDetail = new CustomUserDetail();
      anonymousUserDetail.setAnonymous(true);
      return new AnonymousAuthenticationToken("anonymousUser", anonymousUserDetail,
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
    }

    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      Object principal = authentication.getPrincipal();
      String password = (String) authentication.getCredentials();

      UserDetails userDetails;
      try {
        if (principal instanceof Long) {
          userDetails = userDetailsService.loadUserById((Long) principal);
        } else if (principal instanceof String) {
          userDetails = userDetailsService.loadUserByUsername((String) principal);
        } else {
          throw new AuthenticationException("Unsupported principal type") {
          };
        }
      } catch (UsernameNotFoundException e) {
        throw new AuthenticationException("User not found") {
        };
      }

      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new AuthenticationException("Invalid password") {
        };
      }

      return new UsernamePasswordAuthenticationToken(userDetails, password,
          userDetails.getAuthorities());
    }

    throw new AuthenticationException("Unsupported authentication type") {
    };
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return AnonymousAuthenticationToken.class.isAssignableFrom(authentication) ||
        UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}