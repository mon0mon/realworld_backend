package kr.neoventureholdings.realword_backend.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.comment.domains.Comment;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
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
public class CommentResponseDto {
  private Long id;
  private String body;
  @JsonProperty("author")
  private ProfileResponseDto author;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;

  public static CommentResponseDto of(Comment comment, User user) {
    return CommentResponseDto.builder()
        .id(comment.getId())
        .body(comment.getBody())
        .author(comment.getUser().getProfile().of(user))
        .createAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .build();
  }
}
