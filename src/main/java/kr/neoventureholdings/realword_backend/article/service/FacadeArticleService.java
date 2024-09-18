package kr.neoventureholdings.realword_backend.article.service;

import java.util.List;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeArticleService {
  private final ArticleService articleService;

  public Article getArticle(String slug) {
    return articleService.getArticle(slug);
  }

  public List<Article> getArticles(ArticleRequestParamDto paramDto) {
    return articleService.getArticles(paramDto);
  }

  public Article saveArticle(ArticleRequestDto articleRequestDto, CustomUserDetail userDetail) {
    return articleService.saveArticle(articleRequestDto, userDetail);
  }

  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug, CustomUserDetail userDetail) {
    return articleService.updateArticle(articleRequestDto, slug, userDetail);
  }

  public void deleteArticle(String slug, CustomUserDetail userDetail) {
    articleService.deleteArticle(slug, userDetail);
  }
}
