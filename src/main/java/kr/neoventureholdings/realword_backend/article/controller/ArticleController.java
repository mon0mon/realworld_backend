package kr.neoventureholdings.realword_backend.article.controller;

import kr.neoventureholdings.realword_backend.article.service.FacadeArticleService;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
  private final FacadeArticleService articleService;

  @GetMapping
  public ResponseEntity<CommonResponseDto> getArticles(
      @RequestParam("author") String author,
      @RequestParam("favorited") String favoritedByUsername,
      @RequestParam("tag") String tag,
      @RequestParam(name = "limit", value = "20") Integer limit,
      @RequestParam(name = "offset", value = "0") Integer offset
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .build()
        );
  }

  @PostMapping
  public ResponseEntity<CommonResponseDto> saveArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .build()
        );
  }

  @PutMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> updateArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto,
      @PathVariable("slug") String slug
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .build()
        );
  }

  @DeleteMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> deleteArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto,
      @PathVariable("slug") String slug
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .build()
        );
  }
}
