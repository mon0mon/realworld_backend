package kr.neoventureholdings.realword_backend.config.security.authentication;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetail loadUserById(Long userId) throws UsernameNotFoundException {
    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("No Match User"));
    return CustomUserDetail.of(user);
  }

  @Override
  public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No Match User"));
    return CustomUserDetail.of(user);
  }
}
