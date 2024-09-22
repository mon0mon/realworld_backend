package kr.neoventureholdings.realword_backend.auth.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.repository.UserRepository;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.config.security.jwt.JwtTokenProvider;
import kr.neoventureholdings.realword_backend.exception.auth.UserLoginException;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
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
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User register(UserRequestDto requestDto) {
    checkUserExists(requestDto.getEmail());
    return userRepository.save(requestDto.toUser(passwordEncoder));
  }

  @Transactional(readOnly = true)
  public UserResponseDto getUserDto(CustomUserDetail customUserDetail) {
    return findUserByCustomUserDetail(customUserDetail).to();
  }

  @Transactional
  public User getUser(CustomUserDetail customUserDetail) {
    return findUserByCustomUserDetail(customUserDetail);
  }

  @Transactional
  public User login(UserRequestDto requestDto) {
    return findUserByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword());
  }

  @Transactional
  public User update(UserRequestDto requestDto, String accessToken) {
    User user = fromAccessToken(accessToken);
    user = user.of(requestDto, passwordEncoder);
    return userRepository.save(user);
  }

  private User fromAccessToken(String accessToken) {
    return findUserById(jwtTokenProvider.decodeToken(accessToken).getUserId());
  }

  public String getAccessToken(User user) {
    return getAccessToken(user.getId());
  }

  public String getAccessToken(Long userId) {
    return jwtTokenProvider.createAccessToken(userId).getToken();
  }

  @Transactional(readOnly = true)
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

  private boolean checkUserExists(String email) {
    assert StringUtils.hasText(email);
    return userRepository.findByEmail(email).isPresent();
  }
}
