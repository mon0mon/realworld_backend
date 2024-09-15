package kr.neoventureholdings.realword_backend.auth.controller;

import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.service.UserService;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final CookieUtil cookieUtil;

  /**
   * 신규 이용자 등록
   *
   * @return
   */
  @PostMapping("/users")
  public ResponseEntity<CommonResponseDto> registerUser(@Validated(UserRequestDto.Registration.class) @RequestBody CommonRequestDto commonRequestDto) {
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
  public ResponseEntity<String> getCurrentUser() {
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  /**
   * 사용자 로그인
   *
   * @return
   */
  @PostMapping("/users/login")
  public ResponseEntity<CommonResponseDto> login(@Validated(UserRequestDto.Login.class) @RequestBody CommonRequestDto commonRequestDto) {
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
  public ResponseEntity<String> updateUser() {
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }
}
