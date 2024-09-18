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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kr.neoventureholdings.realword_backend.article.dto.ArticleDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleResponseDto;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.common.entity.BaseEntity;
import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import kr.neoventureholdings.realword_backend.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.StringUtils;

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
@NamedEntityGraph(name= "Article.withUser", attributeNodes = @NamedAttributeNode("author"))
@NamedEntityGraph(name = "Article.withUserAndFavorites", attributeNodes = {
    @NamedAttributeNode("author"),
    @NamedAttributeNode("favorites")
})
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
  @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
  private Set<Favorite> favorites;

  public void addFavorite(Favorite favorite) {
    favorites.add(favorite);
  }

  public void removeFavorite(Favorite favorite) {
    favorites.remove(favorite);
  }

  public static Article of(ArticleDto dto, User user) {
    assert dto.getSlug() != null;

    return Article
        .builder()
        .title(dto.getTitle())
        .body(dto.getBody())
        .description(dto.getDescription())
        .author(dto.getAuthor())
        .slug(dto.getSlug())
        .author(user)
        .build();
  }

  public Article of(ArticleDto dto) {
    if (dto.getAuthor() != null) {
      setAuthor(dto.getAuthor());
    }

    if (StringUtils.hasText(dto.getTitle())) {
      setTitle(dto.getTitle());
    }

    if (StringUtils.hasText(dto.getBody())) {
      setBody(dto.getBody());
    }

    if (StringUtils.hasText(dto.getDescription())) {
      setDescription(dto.getDescription());
    }

    if (StringUtils.hasText(dto.getSlug())) {
      setSlug(dto.getSlug());
    }

    return this;
  }

  public ArticleResponseDto to() {
    return ArticleResponseDto
        .builder()
        .slug(getSlug())
        .title(getTitle())
        .description(getDescription())
        .body(getBody())
        .createAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .favorited(
            getFavorites()
                .stream()
                .anyMatch(fav -> fav.getUser().getId().equals(SecurityUtil.getCurrentUserId()))
        )
        .favoritesCount(getFavorites().size())
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
