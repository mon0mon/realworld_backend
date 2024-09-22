package kr.neoventureholdings.realword_backend.auth.controller;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final FacadeUserService facadeUserService;

  /**
   * 신규 이용자 등록
   *
   * @return
   */
  @PostMapping("/users")
  public ResponseEntity<CommonResponseDto> registerUser(
      @Validated(UserRequestDto.Registration.class) @RequestBody CommonRequestDto commonRequestDto
  ) {
    UserRequestDto userRequestDto = commonRequestDto.getUser();
    User user = facadeUserService.register(userRequestDto);
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .userResponseDto(facadeUserService.getUserResponseDto(user))
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
    User user = facadeUserService.getCurrentUser(userDetail);
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .userResponseDto(facadeUserService.getUserResponseDto(user))
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

    User user = facadeUserService.login(commonRequestDto.getUser());
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .userResponseDto(
                facadeUserService.getUserResponseDto(user)
            )
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
      @RequestBody CommonRequestDto commonRequestDto) {
    User user = facadeUserService.update(commonRequestDto.getUser());
    return ResponseEntity
        .ok()
        .body(
            CommonResponseDto
                .builder()
                .userResponseDto(facadeUserService.getUserResponseDto(user))
                .build()
        );
  }
}
