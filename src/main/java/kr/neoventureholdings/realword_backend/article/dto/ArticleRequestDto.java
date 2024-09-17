package kr.neoventureholdings.realword_backend.article.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
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
public class ArticleRequestDto {
  @NotBlank(groups = {Create.class})
  private String title;
  @NotBlank(groups = {Create.class})
  private String description;
  @NotBlank(groups = {Create.class})
  private String body;
  private List<String> tagList;

  //  Validation Groups
  public interface Create {}
  public interface Update {}
}
