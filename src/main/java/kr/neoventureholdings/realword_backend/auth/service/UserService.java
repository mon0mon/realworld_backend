package kr.neoventureholdings.realword_backend.auth.service;

import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
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
  public void register(UserRequestDto requestDto) {
    findUserByUsername(requestDto.getUsername());
    userRepository.save(requestDto.toUser(passwordEncoder));
  }

  public UserResponseDto getUserDto(String username) {
    User user = findUserByUsername(username);
    return user.userResponseDto();
  }

  public UserResponseDto login(UserRequestDto requestDto) {
    User user = findUserByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword());
    return user.userResponseDto(getAccessToken(user.getId()));
  }

  public UserResponseDto update() {
    return null;
  }

  private User fromJwtInfo(JWTInfo jwtInfo) {
    return findUserById(jwtInfo.getUserId());
  }

  public AccessTokenResponseDto getAccessToken(User user) {
    return jwtTokenProvider.createAccessToken(user.getId());
  }

  public String getAccessToken(Long userId) {
    return jwtTokenProvider.createAccessToken(userId).getToken();
  }

  //  TODO ExceptionHandler로 처리하도록
  private User findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private User findUserByEmailAndPassword(String email, String password) {
    return userRepository.findByEmailAndPassword(email, password)
        .orElseThrow(() -> new LoginException("UsernamePassword Authentication Failed"));
  }

  private User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }
}