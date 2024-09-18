package kr.neoventureholdings.realword_backend.favorite.dto;

import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
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
public class FavoriteDto {
  private User user;
  private Article article;

  public Favorite to() {
    return Favorite
        .builder()
        .user(getUser())
        .article(getArticle())
        .build();
  }
}