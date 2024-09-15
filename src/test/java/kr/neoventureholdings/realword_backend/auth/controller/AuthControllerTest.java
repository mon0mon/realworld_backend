package kr.neoventureholdings.realword_backend.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();
  User user;

  @BeforeEach
  void beforeEach() {
    user = User.builder()
        .email("user1@examle.com")
        .username("user1")
        .password("pass")
        .build();
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 로그인")
  void loginTest() {
    Assertions.catchThrowable(
        () -> mockMvc.perform(post("/users/login")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
    );
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 회원가입")
  void registerTest() {
    user = User.builder()
        .email("user2@example.com")
        .username("user2")
        .password("pass")
        .build();

    Assertions.catchThrowable(
        () -> mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
    );
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