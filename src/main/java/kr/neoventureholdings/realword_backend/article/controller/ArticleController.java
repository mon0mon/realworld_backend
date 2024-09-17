package kr.neoventureholdings.realword_backend.article.controller;

import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.service.FacadeArticleService;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

  private final FacadeArticleService articleService;

  @GetMapping
  public ResponseEntity<CommonResponseDto> getArticle(
      @ModelAttribute ArticleRequestParamDto paramDto) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDtoList(articleService.getArticles(paramDto)
                .stream()
                .map(Article::to)
                .toList()
            )
            .build()
        );
  }

  @GetMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> getArticles(@PathVariable("slug") String slug) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(articleService.getArticle(slug).to())
            .build()
        );
  }

  @PostMapping
  public ResponseEntity<CommonResponseDto> saveArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(
                articleService.saveArticle(commonRequestDto.getArticle(), userDetail)
                    .to()
            )
            .build()
        );
  }

  @PutMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> updateArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto,
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(
                articleService.updateArticle(commonRequestDto.getArticle(), slug, userDetail)
                    .to()
            )
            .build()
        );
  }

  @DeleteMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> deleteArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    articleService.deleteArticle(slug, userDetail);
    return ResponseEntity.ok(null);
  }
}
