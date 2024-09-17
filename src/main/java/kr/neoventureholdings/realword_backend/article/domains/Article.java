package kr.neoventureholdings.realword_backend.article.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import java.util.List;
import kr.neoventureholdings.realword_backend.article.dto.ArticleResponseDto;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article", indexes = {
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_user", columnList = "author")
})
@NamedEntityGraph(
    name = "Article.withUser",
    attributeNodes = @NamedAttributeNode("author")
)
public class Article extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //  Article에서는 User에 대해 반드시 조회하므로, FetchType.Eager에 EntityGraph 사용 할 것
  @ManyToOne(fetch = FetchType.EAGER)
  @Column(name = "author")
  private User author;

  private String title;
  private String description;
  private String body;
  @Column(unique = true)
  private String slug;

  public ArticleResponseDto to() {
    return ArticleResponseDto
        .builder()
        .slug(getSlug())
        .title(getTitle())
        .description(getDescription())
        .body(getBody())
        .createAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .favorited(null)
        .favoritesCount(null)
        .author(getAuthor().to())
        .tags(List.of())
        .build();
  }
}
