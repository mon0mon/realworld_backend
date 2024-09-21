package kr.neoventureholdings.realword_backend.article.controller;

import kr.neoventureholdings.realword_backend.article.dto.ArticleListResponseDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleParamType;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.service.FacadeArticleService;
import kr.neoventureholdings.realword_backend.comment.dto.CommentResponseDto;
import kr.neoventureholdings.realword_backend.comment.service.FacadeCommentService;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

  private final FacadeArticleService facadeArticleService;
  private final FacadeCommentService facadeCommentService;

  @GetMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> getArticle(
      @PathVariable("slug") String slug
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(facadeArticleService.getArticle(slug).to())
            .build()
        );
  }

  @GetMapping
  public ResponseEntity<CommonResponseDto> getArticles(
      @Validated @ModelAttribute ArticleRequestParamDto paramDto
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDtoList(
                ArticleListResponseDto.of(facadeArticleService.getArticles(paramDto))
            )
            .build()
        );
  }

  @GetMapping("/feed")
  public ResponseEntity<CommonResponseDto> getFeeds(
      @Validated @ModelAttribute ArticleRequestParamDto paramDto
  ) {
    paramDto.setParamType(ArticleParamType.FEED);
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDtoList(
                ArticleListResponseDto.of(facadeArticleService.getArticles(paramDto))
            )
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
                facadeArticleService.saveArticle(commonRequestDto.getArticle(), userDetail)
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
                facadeArticleService.updateArticle(commonRequestDto.getArticle(), slug, userDetail)
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
    facadeArticleService.deleteArticle(slug, userDetail);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(null);
  }

  @PostMapping("/{slug}/favorite")
  public ResponseEntity<CommonResponseDto> favoriteArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    facadeArticleService.favoriteArticle(slug, userDetail);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(null);
  }

  @DeleteMapping("/{slug}/favorite")
  public ResponseEntity<CommonResponseDto> unfavoriteArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    facadeArticleService.unfavoriteArticle(slug, userDetail);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(null);
  }

  @GetMapping("/{slug}/comments")
  public ResponseEntity<CommonResponseDto> getComments(
      @PathVariable("slug") String slug
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .commentResponseDtoList(
                facadeCommentService.getComments(slug).stream().map(CommentResponseDto::of)
                    .toList())
            .build()
        );
  }

  @PostMapping("/{slug}/comments")
  public ResponseEntity<CommonResponseDto> createComment(
      @PathVariable("slug") String slug,
      @Validated @RequestBody CommonRequestDto requestDto
  ) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .commentResponseDto(
                CommentResponseDto.of(
                    facadeCommentService.createComment(slug, requestDto.getComment())))
            .build()
        );
  }

  @DeleteMapping("/{slug}/comments/{id}")
  public ResponseEntity<CommonResponseDto> deleteComment(
      @PathVariable("slug") String slug,
      @PathVariable("id") Long commentId
  ) {
    facadeCommentService.deleteComment(slug, commentId);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }
}
