package kr.neoventureholdings.realword_backend.auth.controller;

import kr.neoventureholdings.realword_backend.auth.dto.AccessTokenResponseDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserLoginRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.auth.service.UserService;
import kr.neoventureholdings.realword_backend.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  public ResponseEntity<String> registerUser() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  /**
   * 현재 로그인 한 사용자 정보
   *
   * @param userDetails
   * @return
   */
  @GetMapping("/user")
  public ResponseEntity<UserDetails> getCurrentUser(
      @AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }

  /**
   * 사용자 로그인
   *
   * @return
   */
  @PostMapping("/login")
  public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequestDto loginRequestDto) {
    UserResponseDto userResponseDto = userService.login(loginRequestDto);
    AccessTokenResponseDto renewedAccessToken = userService.getAccessToken(userResponseDto);
    ResponseCookie accessTokenCookie = cookieUtil.setAccessToken(renewedAccessToken);

    return ResponseEntity
        .ok()
        .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
        .body(userResponseDto);
  }

  /**
   * 사용자 정보 업데이트
   *
   * @param userDetails
   * @return
   */
  @PutMapping("/user")
  public ResponseEntity<UserDetails> updateUser(@AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }
}
