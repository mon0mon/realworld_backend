package kr.neoventureholdings.realword_backend.config.security.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    Object principal = authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    UserDetails userDetails;
    try {
      if (principal instanceof Long ) {
        userDetails = userDetailsService.loadUserById((Long) principal);
      } else if (principal instanceof String) {
        userDetails = userDetailsService.loadUserByUsername((String) principal);
      } else {
        throw new AuthenticationException("Unsupported principal type") {};
      }
    } catch (UsernameNotFoundException e) {
      throw new AuthenticationException("User not found") {};
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new AuthenticationException("Invalid password") {};
    }

    return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}