package kr.neoventureholdings.realword_backend.auth.service;

import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.repository.UserRepository;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetailsService;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider;
import kr.neoventureholdings.realword_backend.exception.auth.UserLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserResponseDto register(UserRequestDto requestDto) {
    checkUserExists(requestDto.getEmail());
    return userRepository.save(requestDto.toUser(passwordEncoder)).to();
  }

  public UserResponseDto getUserDto(CustomUserDetail customUserDetail) {
    return findUserByCustomUserDetail(customUserDetail).to();
  }

  public User getUser(CustomUserDetail customUserDetail) {
    return findUserByCustomUserDetail(customUserDetail);
  }

  @Transactional
  public UserResponseDto login(UserRequestDto requestDto) {
    User user = findUserByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword());
    return user.to(getAccessToken(user.getId()));
  }

  @Transactional
  public UserResponseDto update(UserRequestDto requestDto, String accessToken) {
    User user = fromAccessToken(accessToken);
    user = user.of(requestDto, passwordEncoder);
    return userRepository.save(user).to(getAccessToken(user.getId()));
  }

  private User fromAccessToken(String accessToken) {
    return findUserById(jwtTokenProvider.decodeToken(accessToken).getUserId());
  }

  public AccessTokenResponseDto getAccessToken(User user) {
    return jwtTokenProvider.createAccessToken(user.getId());
  }

  public String getAccessToken(Long userId) {
    return jwtTokenProvider.createAccessToken(userId).getToken();
  }

  public User getRefreshUser(User user) {
    return userRepository.findById(user.getId())
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private User findUserByCustomUserDetail(CustomUserDetail customUserDetail) {
    return userRepository.findById(customUserDetail.getId())
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private User findUserByEmailAndPassword(String email, String password) {
    return userRepository.findByEmailAndPassword(email, password)
        .orElseThrow(() -> new UserLoginException("UsernamePassword Authentication Failed"));
  }

  private User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private User findUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("No Such User Element"));
  }

  private boolean checkUserExists(String email) {
    assert StringUtils.hasText(email);
    return userRepository.findByEmail(email).isPresent();
  }
}
