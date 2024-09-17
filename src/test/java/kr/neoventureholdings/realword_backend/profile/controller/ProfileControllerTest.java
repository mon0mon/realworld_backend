package kr.neoventureholdings.realword_backend.profile.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.TestConstant;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.constant.TokenConstant;
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

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {
  @Autowired
  private MockMvc mockMvc;
  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("사용자 프로필 컨트롤러 테스트 - 프로필 (로그인 X)(팔로우 X)")
  void getProfileWithoutLoginWithoutFollow() {
    try {
      mockMvc.perform(get("/profiles/" + TestConstant.PROFILE_USERNAME)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.profile.username").value(TestConstant.PROFILE_USERNAME),
              jsonPath("$.profile.bio").value(TestConstant.PROFILE_BIO),
              jsonPath("$.profile.image").value(TestConstant.PROFILE_IMAGE),
              jsonPath("$.profile.following").value(false)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("사용자 프로필 컨트롤러 테스트 - 프로필 (로그인 O)(팔로우 X)")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = TestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void getProfileWithLoginWithoutFollow() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

    String token = userDetails.getToken();

    try {
      mockMvc.perform(get("/profiles/" + TestConstant.PROFILE_USERNAME)
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.profile.username").value(TestConstant.PROFILE_USERNAME),
              jsonPath("$.profile.bio").value(TestConstant.PROFILE_BIO),
              jsonPath("$.profile.image").value(TestConstant.PROFILE_IMAGE),
              jsonPath("$.profile.following").value(false)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
