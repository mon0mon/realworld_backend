package kr.neoventureholdings.realword_backend.article.repository;

import jakarta.persistence.criteria.*;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

  public static Specification<Article> getArticlesByFilters(ArticleRequestParamDto paramDto) {
    return (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.conjunction();

      if (paramDto.getAuthor() != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("author").get("profile").get("username"), paramDto.getAuthor()));
      }

      if (paramDto.getTag() != null) {
        Join<Article, Tag> tags = root.join("tags", JoinType.INNER);
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(tags.get("value"), paramDto.getTag()));
      }

      if (paramDto.getFavorited() != null) {
        Join<Article, Favorite> favorites = root.join("favorites", JoinType.INNER);
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(favorites.get("user").get("profile").get("username"), paramDto.getFavorited()));
      }

      return predicate;
    };
  }
}