package kr.neoventureholdings.realword_backend.tag.domains;

import jakarta.persistence.*;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tags {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  public Tags(Article article, Tag tag) {
    this.article = article;
    this.tag = tag;
  }
}