package kr.neoventureholdings.realword_backend.article.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @EntityGraph(value = "Article.withUser")
  public Optional<Article> findById(Long id);
  @EntityGraph(value = "Article.withUser")
  public Optional<Article> findArticlesByAuthor(User author);
  @EntityGraph(value = "Article.withUser")
  public Optional<Article> findArticleBySlug(String slug);
  @EntityGraph(value = "Article.withUser")
  public Optional<Article> findArticleByAuthorAndSlug(User author, String slug);
}
