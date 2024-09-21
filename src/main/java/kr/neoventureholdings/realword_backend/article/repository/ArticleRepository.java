package kr.neoventureholdings.realword_backend.article.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>,
    JpaSpecificationExecutor<Article> {

  public Optional<Article> findById(Long id);

  public Optional<Article> findArticlesByAuthor(User author);

  public Optional<Article> findArticleBySlug(String slug);

  @EntityGraph(value = "Article.withUserAndFavoritesAndTags")
  public Optional<Article> findArticleByAuthorAndSlug(User author, String slug);

  @EntityGraph(value = "Article.withUserAndFavoritesAndTags")
  public Page<Article> findAll(Specification<Article> spec, Pageable pageable);
}
