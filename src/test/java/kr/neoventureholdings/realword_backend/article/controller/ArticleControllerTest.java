package kr.neoventureholdings.realword_backend.article.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.article.ArticleTestConstant;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.auth.AuthTestConstant;
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
class ArticleControllerTest {

  @Autowired
  private MockMvc mockMvc;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void beforeAll() {
    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 단일 조회 (로그인 X)")
  void getArticleBySlug() {
    try {
      mockMvc.perform(get("/articles/" + ArticleTestConstant.PREDEFINED_RESULT_SLUG)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.article.slug").value(ArticleTestConstant.PREDEFINED_RESULT_SLUG),
              jsonPath("$.article.title").value(ArticleTestConstant.PREDEFINED_RESULT_TITLE),
              jsonPath("$.article.description").value(
                  ArticleTestConstant.PREDEFINED_RESULT_DESCRIPTION),
              jsonPath("$.article.body").value(ArticleTestConstant.PREDEFINED_RESULT_BODY),
              jsonPath("$.article.author.username").value(
                  ArticleTestConstant.PREDEFINED_RESULT_AUTHOR_USERNAME)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 목록 조회 (로그인 X)")
  void getArticleList() {
    try {
      mockMvc.perform(get("/articles")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.articlesCount").value(11)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 목록 조회 (로그인 X)(Filter : Author)(Author : celeb)")
  void getArticleListByAuthor() {
    try {
      mockMvc.perform(get("/articles")
              .queryParam("author", ArticleTestConstant.DEFAULT_AUTHOR)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.articlesCount").value(6),
              jsonPath("$.articles[*].author.username")
                  .value(Matchers.everyItem(
                      Matchers.equalTo(ArticleTestConstant.DEFAULT_AUTHOR))
                  )
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 목록 조회 (로그인 X)(Filter : Author)(Author : user1)")
  void getArticleListByAuthor2() {
    try {
      mockMvc.perform(get("/articles")
              .queryParam("author", ArticleTestConstant.DEFAULT_AUTHOR2)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.articlesCount").value(5),
              jsonPath("$.articles[*].author.username")
                  .value(Matchers.everyItem(
                      Matchers.equalTo(ArticleTestConstant.DEFAULT_AUTHOR2))
                  )
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 등록")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void createArticle() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();

      ArticleRequestDto articleRequestDto = ArticleRequestDto
          .builder()
          .title(ArticleTestConstant.CREATE_TITLE)
          .description(ArticleTestConstant.CREATE_DESCRIPTION)
          .body(ArticleTestConstant.CREATE_BODY)
          .tagList(ArticleTestConstant.CREATE_TAGLIST)
          .build();
      CommonRequestDto requestDto = CommonRequestDto
          .builder()
          .article(articleRequestDto)
          .build();

      mockMvc.perform(post("/articles")
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.article.slug").value(ArticleTestConstant.CREATE_SLUG),
              jsonPath("$.article.title").value(ArticleTestConstant.CREATE_TITLE),
              jsonPath("$.article.description").value(ArticleTestConstant.CREATE_DESCRIPTION),
              jsonPath("$.article.body").value(ArticleTestConstant.CREATE_BODY),
              jsonPath("$.article.author.username").value(AuthTestConstant.USERNAME)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 수정")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void updateArticle() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();

      ArticleRequestDto articleRequestDto = ArticleRequestDto
          .builder()
          .title(ArticleTestConstant.UPDATE_TITLE)
          .description(ArticleTestConstant.UPDATE_DESCRIPTION)
          .body(ArticleTestConstant.UPDATE_BODY)
          .tagList(ArticleTestConstant.UPDATE_TAGlIST)
          .build();
      CommonRequestDto requestDto = CommonRequestDto
          .builder()
          .article(articleRequestDto)
          .build();

      mockMvc.perform(put("/articles/" + ArticleTestConstant.UPDATE_TARGET_SLUG)
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.article.slug").value(ArticleTestConstant.UPDATE_SLUG),
              jsonPath("$.article.title").value(ArticleTestConstant.UPDATE_TITLE),
              jsonPath("$.article.description").value(ArticleTestConstant.UPDATE_DESCRIPTION),
              jsonPath("$.article.body").value(ArticleTestConstant.UPDATE_BODY),
              jsonPath("$.article.author.username").value(AuthTestConstant.USERNAME)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 수정 예외 발생 (SQL Integrity Exception)")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void updateArticleException() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();

      ArticleRequestDto articleRequestDto = ArticleRequestDto
          .builder()
          .title(ArticleTestConstant.UPDATE_EXCEPTION_UNIQUEVIOLATION_TITLE)
          .build();
      CommonRequestDto requestDto = CommonRequestDto
          .builder()
          .article(articleRequestDto)
          .build();

      mockMvc.perform(put("/articles/" + ArticleTestConstant.UPDATE_TARGET_SLUG)
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isUnprocessableEntity(),
              content().contentType(MediaType.APPLICATION_JSON)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 수정 예외 발생 (No Authorization Exception)")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void updateArticleException2() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();

      ArticleRequestDto articleRequestDto = ArticleRequestDto
          .builder()
          .title(ArticleTestConstant.UPDATE_TITLE)
          .build();
      CommonRequestDto requestDto = CommonRequestDto
          .builder()
          .article(articleRequestDto)
          .build();

      mockMvc.perform(
              put("/articles/" + ArticleTestConstant.UPDATE_EXCEPTION_NOAUTHORIZATION_TARGET_SLUG)
                  .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
                  .content(objectMapper.writeValueAsString(requestDto))
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isForbidden(),
              content().contentType(MediaType.APPLICATION_JSON)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 삭제")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void deleteArticle() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();


      mockMvc.perform(delete("/articles/" + ArticleTestConstant.DELETE_TARGET_SLUG)
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

  @Test
  @DisplayName("게시글 컨트롤러 테스트 - 게시글 삭제 예외 발생 (No Authorization Exception)")
  @PreAuthorize("isAuthenticated()")
  @WithUserDetails(value = AuthTestConstant.EMAIL, userDetailsServiceBeanName = "testUserDetailService")
  void deleteArticleException() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

      String token = userDetails.getToken();


      mockMvc.perform(delete("/articles/" + ArticleTestConstant.DELETE_EXCEPTION_NOAUTHORIZATION_TARGET_SLUG)
              .header(HttpHeaders.AUTHORIZATION, TokenConstant.TOKEN_HEADER_PREFIX + token)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isForbidden(),
              content().contentType(MediaType.APPLICATION_JSON)
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
