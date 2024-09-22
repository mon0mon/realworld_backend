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
  public Article saveArticle(ArticleRequestDto articleRequestDto) {
    return articleService.saveArticle(articleRequestDto);
  }

  @Transactional
  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug) {
    return articleService.updateArticle(articleRequestDto, slug);
  }

  @Transactional
  public void deleteArticle(String slug) {
    articleService.deleteArticle(slug);
  }

  @Transactional
  public void favoriteArticle(String slug) {
    articleService.favoriteArticle(slug);
  }

  @Transactional
  public void unfavoriteArticle(String slug) {
    articleService.unfavoriteArticle(slug);
  }
}
