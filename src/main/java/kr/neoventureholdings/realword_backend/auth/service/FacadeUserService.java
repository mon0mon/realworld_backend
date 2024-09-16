package kr.neoventureholdings.realword_backend.auth.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacadeUserService {
  private final UserService userService;

  @Transactional
  public UserResponseDto register(UserRequestDto userRequestDto) {
    return userService.register(userRequestDto);
  }

  @Transactional
  public UserResponseDto login(UserRequestDto userRequestDto) {
    return userService.login(userRequestDto);
  }

  @Transactional
  public UserResponseDto update(UserRequestDto userRequestDto, String accessToken) {
    return userService.update(userRequestDto, accessToken);
  }

  @Transactional(readOnly = true)
  public UserResponseDto getCurrentUserResponseDto(CustomUserDetail customUserDetail) {
    return userService.getUserDto(customUserDetail);
  }

  @Transactional(readOnly = true)
  public User getCurrentUser(CustomUserDetail customUserDetail) {
    return userService.getUser(customUserDetail);
  }
}
