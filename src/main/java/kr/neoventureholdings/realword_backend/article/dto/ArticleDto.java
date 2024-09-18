package kr.neoventureholdings.realword_backend.article.dto;

import java.util.List;
import java.util.UUID;
import kr.neoventureholdings.realword_backend.auth.domains.User;
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
public class ArticleDto {
  private String title;
  private String description;
  private String body;
  private String slug;
  private User author;
  private List<String> tagList;

  public static ArticleDto of(ArticleRequestDto requestDto) {
    return ArticleDto
        .builder()
        .title(requestDto.getTitle())
        .body(requestDto.getBody())
        .description(requestDto.getDescription())
        .tagList(requestDto.getTagList())
        .slug(UUID.randomUUID().toString())
        .build();
  }

  public static ArticleDto of(ArticleRequestDto requestDto, String slug) {
    return ArticleDto
        .builder()
        .title(requestDto.getTitle())
        .body(requestDto.getBody())
        .description(requestDto.getDescription())
        .tagList(requestDto.getTagList())
        .slug(slug)
        .build();
  }
}
