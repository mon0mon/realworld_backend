package kr.neoventureholdings.realword_backend.auth.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacadeUserService {
  private final UserService userService;

  public User register(UserRequestDto userRequestDto) {
    return userService.register(userRequestDto);
  }

  public User login(UserRequestDto userRequestDto) {
    return userService.login(userRequestDto);
  }

  public User update(UserRequestDto userRequestDto) {
    return userService.update(userRequestDto);
  }

  public User getCurrentUser(CustomUserDetail customUserDetail) {
    return userService.getUser(customUserDetail);
  }

  public UserResponseDto getUserResponseDto(User user) {
    return user.to(userService.getAccessToken(user));
  }
}
