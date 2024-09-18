package kr.neoventureholdings.realword_backend.article.service;

import java.util.List;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.repository.ArticleRepository;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.exception.auth.NoAuthorizationException;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final FacadeUserService facadeUserService;

  public Article getArticle(String slug) {
    return getArticleBySlug(slug);
  }

  //  TODO 필터링 옵션을 어떻게 적용할 것인지 고민해보고 구현하기
  public List<Article> getArticles(ArticleRequestParamDto paramDto) {
    return null;
  }

  public Article saveArticle(ArticleRequestDto articleRequestDto, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = extractRequestDtoToEntity(articleRequestDto, user);

    return articleRepository.save(article);
  }

  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = getArticleBySlug(slug);

    if (!article.getAuthor().equals(user)) {
      throw new NoAuthorizationException("can't update article. you are not a author");
    }

    article = article.of(ArticleDto.of(articleRequestDto), false);

    return articleRepository.save(article);
  }

  public void deleteArticle(String slug, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = getArticleBySlug(slug);

    if (!article.getAuthor().equals(user)) {
      throw new NoAuthorizationException("can't delete article. you are not a author");
    }

    articleRepository.delete(article);
  }

  private Article getArticleBySlug(String slug) {
    return articleRepository.findArticleBySlug(slug)
        .orElseThrow(() -> new NoSuchElementException("no article found"));
  }

  private Article getArticleByAuthorAndSlug(User author, String slug) {
    return articleRepository.findArticleByAuthorAndSlug(author, slug)
        .orElseThrow(() -> new NoSuchElementException("no article found"));
  }

  private Article extractRequestDtoToEntity(ArticleRequestDto articleRequestDto, User user) {
    ArticleDto articleDto = ArticleDto.of(articleRequestDto);
    Article article = Article.of(articleDto, user);
    return article;
  }
}
