package kr.neoventureholdings.realword_backend.article.repository;

import jakarta.persistence.criteria.*;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestParamDto;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

  public static Specification<Article> getArticlesByFilters(ArticleRequestParamDto paramDto) {
    return (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.conjunction();

      if (paramDto.getAuthor() != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("author").get("profile").get("username"), paramDto.getAuthor()));
      }

      if (paramDto.getTag() != null) {
        //  TODO Tag Entity 추가 후에 작업 진행할 것
      }

      if (paramDto.getFavoritedByUsername() != null) {
        //  TODO Favorite Entity 추가 후에 작업 진행할 것
      }

      return predicate;
    };
  }
}