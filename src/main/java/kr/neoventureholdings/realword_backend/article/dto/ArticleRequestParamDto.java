package kr.neoventureholdings.realword_backend.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
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
  @Null(groups = Feed.class)
  private String author;
  @JsonProperty("favorited")
  @Null(groups = Feed.class)
  private String favorited;
  @JsonProperty("tag")
  @Null(groups = Feed.class)
  private String tag;
  @JsonProperty("limit")
  @Max(100)
  @Min(1)
  private Integer limit = 20;
  @JsonProperty("offset")
  @Min(0)
  private Integer offset = 0;
  @Builder.Default
  private ArticleParamType paramType = ArticleParamType.ARTICLE;

  //  Validation Groups
  public interface Article {

  }

  public interface Feed {

  }
}
