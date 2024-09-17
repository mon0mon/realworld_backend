package kr.neoventureholdings.realword_backend.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.TestConstant;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.constant.TokenConstant;
import kr.neoventureholdings.realword_backend.util.AuthTestUtils;
import kr.neoventureholdings.realword_backend.util.TestUserDetailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;
  private final static ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void beforeAll() {
    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 로그인")
  void loginTest() {
    try {
      mockMvc.perform(post("/users/login")
              .content(
                  objectMapper.writeValueAsString(AuthTestUtils.getLoginDefaultUserRequest())
              )
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpect(status().isOk());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 회원가입")
  void registerTest() {
    Assertions.catchThrowable(
        () -> mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(AuthTestUtils.getRegisterUserRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
    );
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 현재 로그인한 사용자 정보")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = TestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void getCurrentLoginUserTest() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

    String token = userDetails.getToken();

    try {
      mockMvc.perform(get("/user")
          .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andDo(log())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.user.email").value(TestConstant.EMAIL),
              jsonPath("$.user.username").value(TestConstant.USERNAME),
              jsonPath("$.user.bio").doesNotExist(),
              jsonPath("$.user.image").doesNotExist()
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("사용자 인증 컨트롤러 테스트 - 사용자 정보 수정")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = TestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void updateUserInfoTest() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

    String token = userDetails.getToken();
    try {
      // 사용자 정보 수정 요청
      MvcResult updateResult = mockMvc
          .perform(put("/user")
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .content(objectMapper.writeValueAsString(AuthTestUtils.getUpdateUserRequest()))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpect(status().isOk())
          .andReturn();  // 결과를 반환하여 이후에 사용할 수 있도록

      // 응답 검증
      mockMvc.perform(get("/user")
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.user.email").value(TestConstant.UPDATE_EMAIL))
          .andExpect(jsonPath("$.user.username").value(TestConstant.UPDATE_USERNAME))
          .andExpect(jsonPath("$.user.bio").value(TestConstant.UPDATE_BIO))
          .andExpect(jsonPath("$.user.image").value(TestConstant.UPDATE_IMAGE));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}