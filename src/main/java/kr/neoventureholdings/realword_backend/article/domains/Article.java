package kr.neoventureholdings.realword_backend.article.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import kr.neoventureholdings.realword_backend.auth.domains.User;
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
@Table(name = "article")
@NamedEntityGraph(
    name = "Article.withUser",
    attributeNodes = @NamedAttributeNode("user")
)
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //  Article에서는 User에 대해 반드시 조회하므로, FetchType.Eager에 EntityGraph 사용 할 것
  @ManyToOne(fetch = FetchType.EAGER)
  private User user;
}
