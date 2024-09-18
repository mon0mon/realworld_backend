package kr.neoventureholdings.realword_backend.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class ArticleListResponseDto {
  private List<ArticleResponseDto> articles;
  @JsonProperty("articlesCount")
  private Integer articlesCount;

  public static ArticleListResponseDto of(Page<Article> articlePage) {
    return ArticleListResponseDto
        .builder()
        .articlesCount(articlePage.getNumberOfElements())
        .articles(
            articlePage.getContent()
                .stream()
                .map(Article::to)
                .toList()
        )
        .build();
  }
}
