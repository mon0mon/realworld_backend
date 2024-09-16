package kr.neoventureholdings.realword_backend.auth.controller;

import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final FacadeUserService userService;

  /**
   * 신규 이용자 등록
   *
   * @return
   */
  @PostMapping("/users")
  public ResponseEntity<CommonResponseDto> registerUser(
      @Validated(UserRequestDto.Registration.class) @RequestBody CommonRequestDto commonRequestDto) {
    UserRequestDto userRequestDto = commonRequestDto.getUser();
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .userResponseDto(userService.register(userRequestDto))
            .build()
        );
  }

  /**
   * 현재 로그인 한 사용자 정보
   *
   * @param
   * @return
   */
  @GetMapping("/user")
  public ResponseEntity<CommonResponseDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetail userDetail) {
    UserResponseDto userDto = userService.getCurrentUserResponseDto(userDetail);
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .userResponseDto(userDto)
            .build()
        );
  }

  /**
   * 사용자 로그인
   *
   * @return
   */
  @PostMapping("/users/login")
  public ResponseEntity<CommonResponseDto> login(
      @Validated(UserRequestDto.Login.class) @RequestBody CommonRequestDto commonRequestDto) {
    UserResponseDto userResponseDto = userService.login(commonRequestDto.getUser());

    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .userResponseDto(userResponseDto)
            .build()
        );
  }

  /**
   * 사용자 정보 업데이트
   *
   * @param
   * @return
   */
  @PutMapping("/user")
  public ResponseEntity<CommonResponseDto> updateUser(
      @RequestBody CommonRequestDto commonRequestDto,
      @RequestAttribute("access_token") String accessToken) {
    UserResponseDto userResponseDto = userService.update(commonRequestDto.getUser(),
        accessToken);
    return ResponseEntity
        .ok()
        .body(
            CommonResponseDto
                .builder()
                .userResponseDto(userResponseDto)
                .build()
        );
  }
}
