package kr.neoventureholdings.realword_backend.tag.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TagControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("태그 컨트롤러 테스트 - 목록 조회")
  void getArticleListByAuthor() {
    try {
      mockMvc.perform(get("/tags")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              jsonPath("$.tags").isArray(),
              jsonPath("$.tags", Matchers.hasSize(5)),
              jsonPath("$.tags", Matchers.containsInAnyOrder(
                  "C Family",
                  "Java",
                  "Javascript",
                  "Language",
                  "Web Framework"
              ))
          );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
