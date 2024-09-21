package kr.neoventureholdings.realword_backend.tag.repository;

import java.util.Set;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import kr.neoventureholdings.realword_backend.tag.domains.TagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapRepository extends JpaRepository<TagMap, Long> {
  public Set<Tag> findAllByArticle(Article article);
  public Set<Article> findAllByTag(Tag tag);
}
