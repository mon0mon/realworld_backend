package kr.neoventureholdings.realword_backend.article.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.article.repository.ArticleRepository;
import kr.neoventureholdings.realword_backend.article.repository.ArticleSpecification;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.exception.auth.NoAuthorizationException;
import kr.neoventureholdings.realword_backend.exception.common.EntityAlreadyExistsException;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
import kr.neoventureholdings.realword_backend.exception.common.UniqueConstraintViolationException;
import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import kr.neoventureholdings.realword_backend.favorite.dto.FavoriteDto;
import kr.neoventureholdings.realword_backend.favorite.service.FacadeFavoriteService;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import kr.neoventureholdings.realword_backend.tag.service.FacadeTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final FacadeUserService facadeUserService;
  private final FacadeFavoriteService favoriteService;
  private final FacadeTagService facadeTagService;

  public Article getArticle(String slug) {
    return getArticleBySlug(slug);
  }

  //  TODO 필터링 옵션을 어떻게 적용할 것인지 고민해보고 구현하기
  public Page<Article> getArticles(ArticleRequestParamDto paramDto) {
    Pageable pageable = PageRequest.of(paramDto.getOffset(), paramDto.getLimit(),
        Sort.by(Direction.DESC, "id"));
    Specification<Article> specification = ArticleSpecification.getArticlesByFilters(paramDto);
    return articleRepository.findAll(specification, pageable);
  }

  @Transactional
  public Article saveArticle(ArticleRequestDto articleRequestDto, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = extractRequestDtoToEntity(articleRequestDto, user);

    List<Tag> tagList = articleRequestDto.getTagList()
        .stream()
        .map(tagValue -> new Tag(null, tagValue, null))
        .toList();
    tagList = facadeTagService.saveTags(tagList);

    article.setTags(new HashSet<>(tagList));

    return articleRepository.save(article);
  }

  @Transactional
  public Article updateArticle(ArticleRequestDto articleRequestDto, String slug,
      CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    ArticleDto articleDto = ArticleDto.of(articleRequestDto);
    checkSlugIsUnique(slug, articleDto.getSlug());

    Article article = getArticleBySlug(slug);

    if (!article.getAuthor().equals(user)) {
      throw new NoAuthorizationException("can't update article. you are not a author");
    }

    article = article.of(articleDto);

    return articleRepository.save(article);
  }

  @Transactional
  public void deleteArticle(String slug, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = getArticleBySlug(slug);

    if (!article.getAuthor().equals(user)) {
      throw new NoAuthorizationException("can't delete article. you are not an author");
    }

    articleRepository.delete(article);
  }

  @Transactional
  public void favoriteArticle(String slug, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = getArticleBySlug(slug);

    boolean isAlreadyFavorited = article.getFavorites()
        .stream()
        .anyMatch(fav -> fav.getUser().equals(user));

    if (isAlreadyFavorited) {
      throw new EntityAlreadyExistsException("Article is already favorited by the user");
    }

    FavoriteDto favoriteDto = FavoriteDto.builder()
        .article(article)
        .user(user)
        .build();

    article.addFavorite(favoriteDto.to());

    favoriteService.saveFavorite(favoriteDto);
  }

  @Transactional
  public void unfavoriteArticle(String slug, CustomUserDetail userDetail) {
    User user = facadeUserService.getCurrentUser(userDetail);

    Article article = getArticleBySlug(slug);

    Favorite favorite = article.getFavorites()
        .stream()
        .filter(fav -> fav.getUser().equals(user))
        .findAny()
        .orElseThrow(() -> new EntityAlreadyExistsException(
            "You haven't added this article to your favorites"));

    article.removeFavorite(favorite);

    favoriteService.deleteFavorite(favorite);
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

  private void checkSlugIsUnique(String oldSlug, String newSlug) {
    if (oldSlug.equals(newSlug)) {
      return;
    }

    Optional<Article> articleBySlug = articleRepository.findArticleBySlug(newSlug);
    articleBySlug.ifPresent(value -> {
      throw new UniqueConstraintViolationException("slug [" + newSlug + "] is Already Exists");
    });
  }
}
