package kr.neoventureholdings.realword_backend.article.controller;

import kr.neoventureholdings.realword_backend.article.dto.ArticleListResponseDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleParamType;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.service.FacadeArticleService;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
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
  private final FacadeUserService facadeUserService;

  @GetMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> getArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user;

    if (userDetail == null || userDetail.isAnonymous()) {
      user = null;
    } else {
      user = facadeUserService.getCurrentUser(userDetail);
    }

    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(facadeArticleService.getArticle(slug).to(user))
            .build()
        );
  }

  @GetMapping
  public ResponseEntity<CommonResponseDto> getArticles(
      @Validated @ModelAttribute ArticleRequestParamDto paramDto,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user;

    if (userDetail == null || userDetail.isAnonymous()) {
      user = null;
    } else {
      user = facadeUserService.getCurrentUser(userDetail);
    }

    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDtoList(
                ArticleListResponseDto
                    .of(facadeArticleService.getArticles(paramDto), user)
            )
            .build()
        );
  }

  @GetMapping("/feed")
  public ResponseEntity<CommonResponseDto> getFeeds(
      @Validated @ModelAttribute ArticleRequestParamDto paramDto,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user;

    if (userDetail == null || userDetail.isAnonymous()) {
      user = null;
    } else {
      user = facadeUserService.getCurrentUser(userDetail);
    }
    paramDto.setParamType(ArticleParamType.FEED);
    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDtoList(
                ArticleListResponseDto
                    .of(facadeArticleService.getArticles(paramDto), user)
            )
            .build()
        );
  }

  @PostMapping
  public ResponseEntity<CommonResponseDto> saveArticle(
      @Validated @RequestBody CommonRequestDto commonRequestDto,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user = facadeUserService.getCurrentUser(userDetail);

    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(
                facadeArticleService.saveArticle(commonRequestDto.getArticle())
                    .to(user)
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
    User user = facadeUserService.getCurrentUser(userDetail);

    return ResponseEntity
        .ok()
        .body(CommonResponseDto
            .builder()
            .articleResponseDto(
                facadeArticleService.updateArticle(commonRequestDto.getArticle(), slug)
                    .to(user)
            )
            .build()
        );
  }

  @DeleteMapping("/{slug}")
  public ResponseEntity<CommonResponseDto> deleteArticle(
      @PathVariable("slug") String slug
  ) {
    facadeArticleService.deleteArticle(slug);
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
    facadeArticleService.favoriteArticle(slug);
    User user = facadeUserService.getCurrentUser(userDetail);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(CommonResponseDto.builder()
            .articleResponseDto(facadeArticleService.getArticle(slug).to(user))
            .build()
        );
  }

  @DeleteMapping("/{slug}/favorite")
  public ResponseEntity<CommonResponseDto> unfavoriteArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    facadeArticleService.unfavoriteArticle(slug);
    User user = facadeUserService.getCurrentUser(userDetail);
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(CommonResponseDto.builder()
            .articleResponseDto(facadeArticleService.getArticle(slug).to(user))
            .build()
        );
  }

  @GetMapping("/{slug}/comments")
  public ResponseEntity<CommonResponseDto> getComments(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user;

    if (userDetail != null && !userDetail.isAnonymous()) {
      user = facadeUserService.getCurrentUser(userDetail);
    } else {
      user = null;
    }

    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .commentResponseDtoList(
                facadeCommentService.getComments(slug)
                    .stream()
                    .map(entity -> CommentResponseDto.of(entity, user))
                    .toList()
            )
            .build()
        );
  }

  @PostMapping("/{slug}/comments")
  public ResponseEntity<CommonResponseDto> createComment(
      @PathVariable("slug") String slug,
      @Validated @RequestBody CommonRequestDto requestDto,
      @AuthenticationPrincipal CustomUserDetail userDetail
  ) {
    User user = facadeUserService.getCurrentUser(userDetail);

    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .commentResponseDto(
                CommentResponseDto.of(facadeCommentService
                    .createComment(slug, requestDto.getComment()), user)
            )
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
