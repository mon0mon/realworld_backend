package kr.neoventureholdings.realword_backend.auth.controller;

import static org.junit.jupiter.api.Assertions.*;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
class AuthControllerTest {
  User user;

  @BeforeEach
  void beforeEach() {
    user = User.builder()
        .email("test@examle.com")
        .username("testUser")
        .password("1234")
        .build();
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 로그인")
  void loginTest() {

  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 회원가입")
  void registerTest() {

  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 현재 로그인한 사용자 정보")
  void getCurrentLoginUserTest() {

  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 사용자 정보 수정")
  void updateUserInfoTest() {

  }

}