package kr.neoventureholdings.realword_backend.auth.service;

import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserLoginRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserRegisterRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.repository.UserRepository;
import kr.neoventureholdings.realword_backend.config.security.CustomUserDetailsService;
import kr.neoventureholdings.realword_backend.config.security.JwtTokenProvider;
import kr.neoventureholdings.realword_backend.config.security.JwtTokenProvider.JWTInfo;
import kr.neoventureholdings.realword_backend.exception.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void register(UserRegisterRequestDto requestDto) {
    findUserByUsername(requestDto.getUsername());
    userRepository.save(requestDto.toUser(passwordEncoder));
  }

  public UserResponseDto getUserDto(String username) {
    User user = findUserByUsername(username);
    return user.userResponseDto();
  }

  public UserResponseDto login(UserLoginRequestDto requestDto) {
    User user = findUserByUsernameAndPassword(requestDto.getUsername(), requestDto.getPassword());
    return user.userResponseDto();
  }

  public UserResponseDto update() {
    return null;
  }

  private User fromJwtInfo(JWTInfo jwtInfo) {
    return findUserByUsername(jwtInfo.getUsername());
  }

  public AccessTokenResponseDto getAccessToken(UserResponseDto userResponseDto) {
    return jwtTokenProvider.createAccessToken(userResponseDto.getUsername());
  }

  //  TODO ExceptionHandler로 처리하도록
  private User findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private User findUserByUsernameAndPassword(String username, String password) {
    return userRepository.findByUsernameAndPassword(username, password)
        .orElseThrow(() -> new LoginException("UsernamePassword Authentication Failed"));
  }
}