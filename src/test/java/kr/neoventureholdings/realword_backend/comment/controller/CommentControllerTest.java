package kr.neoventureholdings.realword_backend.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.auth.AuthTestConstant;
import kr.neoventureholdings.realword_backend.comment.CommentTestConstant;
import kr.neoventureholdings.realword_backend.comment.dto.CommentRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.constant.TokenConstant;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CommentControllerTest {
  @Autowired
  private MockMvc mockMvc;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void beforeAll() {
    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  @Test
  @DisplayName("댓글 컨트롤러 테스트 - 게시글 댓글 목록 조회")
  void getArticleList() {
    try {
      mockMvc.perform(get("/articles/" + CommentTestConstant.COMMENT_SLUG + "/comments")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.comments").value(Matchers.hasSize(4))
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("댓글 컨트롤러 테스트 - 게시글에 댓글 등록")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void createArticle() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();

      CommentRequestDto commentRequestDto = CommentRequestDto
          .builder()
          .body(CommentTestConstant.COMMENT_BODY)
          .build();
      CommonRequestDto requestDto = CommonRequestDto
          .builder()
          .comment(commentRequestDto)
          .build();

      mockMvc.perform(post("/articles/" + CommentTestConstant.COMMENT_SLUG + "/comments")
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.comment.body").value(CommentTestConstant.COMMENT_BODY),
              jsonPath("$.comment.author.username").value(AuthTestConstant.USERNAME)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("댓글 컨트롤러 테스트 - 댓글 삭제")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.CELEB_EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void deleteComment() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

    String token = userDetails.getToken();

    try {
      mockMvc.perform(delete("/articles/" + CommentTestConstant.COMMENT_SLUG + "/comments/" + CommentTestConstant.COMMENT_ID)
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
