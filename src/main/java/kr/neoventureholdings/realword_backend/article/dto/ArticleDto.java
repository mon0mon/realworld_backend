package kr.neoventureholdings.realword_backend.article.dto;

import io.jsonwebtoken.lang.Strings;
import java.util.List;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.constant.ArticleConstant;
import kr.neoventureholdings.realword_backend.util.StringUtil;
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
        .slug(Strings.hasText(requestDto.getTitle()) ? StringUtil.replaceAllSpecialCharacter(
            requestDto.getTitle().toLowerCase(), ArticleConstant.SLUG_SEPERATOR, true) : null
        )
        .build();
  }
}
