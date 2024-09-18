package kr.neoventureholdings.realword_backend.article.service;

import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeArticleService {
  private final ArticleService articleService;

  public Article getArticle(String slug) {
    return articleService.getArticle(slug);
  }

  public Page<Article> getArticles(ArticleRequestParamDto paramDto) {
    return articleService.getArticles(paramDto);
  }

  @Transactional
  public Article saveArticle(ArticleRequestDto articleRequestDto, CustomUserDetail userDetail) {
    return articleService.saveArticle(articleRequestDto, userDetail);
  }

  @Transactional
  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug, CustomUserDetail userDetail) {
    return articleService.updateArticle(articleRequestDto, slug, userDetail);
  }

  @Transactional
  public void deleteArticle(String slug, CustomUserDetail userDetail) {
    articleService.deleteArticle(slug, userDetail);
  }

  @Transactional
  public void favoriteArticle(String slug, CustomUserDetail userDetail) {
    articleService.favoriteArticle(slug, userDetail);
  }

  @Transactional
  public void unfavoriteArticle(String slug, CustomUserDetail userDetail) {
    articleService.unfavoriteArticle(slug, userDetail);
  }
}
