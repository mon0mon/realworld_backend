package kr.neoventureholdings.realword_backend.article.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import kr.neoventureholdings.realword_backend.article.dto.ArticleResponseDto;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article", indexes = {
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_author", columnList = "author_id")
})
@NamedEntityGraph(
    name = "Article.withUser",
    attributeNodes = @NamedAttributeNode("author")
)
public class Article extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id", nullable = false)
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

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Article article = (Article) o;
    return getId() != null && Objects.equals(getId(), article.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
