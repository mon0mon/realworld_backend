package kr.neoventureholdings.realword_backend.util;

import kr.neoventureholdings.realword_backend.auth.AuthTestConstant;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import kr.neoventureholdings.realword_backend.auth.repository.UserRepository;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TestUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public TestUserDetailService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    if (!StringUtils.hasText(email)) {
      email = AuthTestConstant.EMAIL;
    }

    User user = userRepository.findByEmail(email).get();

    AccessTokenResponseDto accessToken = jwtTokenProvider.createAccessToken(user.getId());

    CustomUserDetail customUserDetail = CustomUserDetail.of(user);

    customUserDetail.setToken(accessToken.getToken());

    return customUserDetail;
  }
}
