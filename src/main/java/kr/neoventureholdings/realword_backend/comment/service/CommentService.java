package kr.neoventureholdings.realword_backend.comment.service;

import kr.neoventureholdings.realword_backend.comment.domains.Comment;
import kr.neoventureholdings.realword_backend.comment.dto.CommentRequestDto;
import kr.neoventureholdings.realword_backend.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  public Comment saveComment(String slug, CommentRequestDto requestDto) {
    return null;
  }

  public void deleteComment(String slug, Long commentId) {

  }
}
