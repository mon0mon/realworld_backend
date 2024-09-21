package kr.neoventureholdings.realword_backend.common.dto;

import jakarta.validation.Valid;
import kr.neoventureholdings.realword_backend.article.dto.ArticleRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.comment.dto.CommentRequestDto;
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
public class CommonRequestDto {
  @Valid
  private UserRequestDto user;
  @Valid
  private ArticleRequestDto article;
  @Valid
  private CommentRequestDto comment;
}
