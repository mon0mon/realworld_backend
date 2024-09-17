package kr.neoventureholdings.realword_backend.article.service;

import java.util.List;
import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.repository.ArticleRepository;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  public Article getArticle(String slug) {
    return getArticleBySlug(slug);
  }

  //  TODO 필터링 옵션을 어떻게 적용할 것인지 고민해보고 구현하기
  public List<Article> getArticles(ArticleRequestParamDto paramDto) {
    return null;
  }

  public Article saveArticle(ArticleRequestDto articleRequestDto, CustomUserDetail userDetail) {
    return null;
  }

  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug, CustomUserDetail userDetail) {
    return null;
  }

  public boolean deleteArticle(String slug, CustomUserDetail userDetail) {
    return true;
  }

  private Article getArticleBySlug(String slug) {
    return articleRepository.findArticleBySlug(slug)
        .orElseThrow(() -> new NoSuchElementException("no article founded"));
  }
}
