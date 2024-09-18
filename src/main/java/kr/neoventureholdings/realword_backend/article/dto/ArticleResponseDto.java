package kr.neoventureholdings.realword_backend.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
  private String slug;
  private String title;
  private String description;
  private String body;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;
  private Boolean favorited;
  private Integer favoritesCount;
  @JsonProperty("author")
  private UserResponseDto author;
  @JsonProperty("tagList")
  private List<String> tags;

  public static ArticleResponseDto of(Article article) {
    return article.to();
  }
}
