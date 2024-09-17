package kr.neoventureholdings.realword_backend.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ArticleRequestParamDto {
  @JsonProperty("author")
  private String author;
  @JsonProperty("favorited")
  private String favoritedByUsername;
  @JsonProperty("tag")
  private String tag;
  @JsonProperty("limit")
  private Integer limit = 20;
  @JsonProperty("offset")
  private Integer offset = 0;
}
