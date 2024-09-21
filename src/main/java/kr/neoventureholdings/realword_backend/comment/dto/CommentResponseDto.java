package kr.neoventureholdings.realword_backend.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.comment.domains.Comment;
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
  private UserResponseDto author;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;

  public static CommentResponseDto of(Comment comment) {
    return CommentResponseDto.builder()
        .id(comment.getId())
        .body(comment.getBody())
        .author(UserResponseDto.of(comment.getUser()))
        .createAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .build();
  }
}
