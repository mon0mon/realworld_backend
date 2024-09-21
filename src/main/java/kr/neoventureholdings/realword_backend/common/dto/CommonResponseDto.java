package kr.neoventureholdings.realword_backend.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import kr.neoventureholdings.realword_backend.article.dto.ArticleListResponseDto;
import kr.neoventureholdings.realword_backend.article.dto.ArticleResponseDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
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
@JsonInclude(Include.NON_NULL)
public class CommonResponseDto {
  @JsonProperty("user")
  private UserResponseDto userResponseDto;
  @JsonProperty("profile")
  private ProfileResponseDto profileResponseDto;
  @JsonProperty("article")
  private ArticleResponseDto articleResponseDto;
  @JsonUnwrapped
  private ArticleListResponseDto articleResponseDtoList;
  @JsonProperty("tags")
  private List<String> tags;
}
