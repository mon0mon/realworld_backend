package kr.neoventureholdings.realword_backend.comment.service;

import java.util.List;
import kr.neoventureholdings.realword_backend.comment.domains.Comment;
import kr.neoventureholdings.realword_backend.comment.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeCommentService {
  private final CommentService commentService;

  public List<Comment> getComments(String slug) {
    return commentService.getComments(slug);
  }

  public Comment createComment(String slug, CommentRequestDto requestDto) {
    return commentService.saveComment(slug, requestDto);
  }

  public void deleteComment(String slug, Long commentId) {
    commentService.deleteComment(slug, commentId);
  }
}
