package kr.neoventureholdings.realword_backend.comment.service;

import java.util.List;
import kr.neoventureholdings.realword_backend.article.domains.Article;
import kr.neoventureholdings.realword_backend.article.service.FacadeArticleService;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.comment.domains.Comment;
import kr.neoventureholdings.realword_backend.comment.dto.CommentRequestDto;
import kr.neoventureholdings.realword_backend.comment.repository.CommentRepository;
import kr.neoventureholdings.realword_backend.exception.auth.NoAuthorizationException;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
import kr.neoventureholdings.realword_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final FacadeUserService facadeUserService;
  private final FacadeArticleService facadeArticleService;

  public List<Comment> getComments(String slug) {
    Article article = facadeArticleService.getArticle(slug);
    return commentRepository.findAllByArticle(article);
  }

  public Comment saveComment(String slug, CommentRequestDto requestDto) {
    User user = facadeUserService.getCurrentUser(SecurityUtil.getCurrentUserCustomUserdetail());
    Article article = facadeArticleService.getArticle(slug);

    Comment comment = Comment.builder()
        .body(requestDto.getBody())
        .article(article)
        .user(user)
        .build();

    return commentRepository.save(comment);
  }

  public void deleteComment(String slug, Long commentId) {
    User user = facadeUserService.getCurrentUser(SecurityUtil.getCurrentUserCustomUserdetail());
    Article article = facadeArticleService.getArticle(slug);

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NoSuchElementException("no such comment"));

    if (!comment.getUser().equals(user)) {
      throw new NoAuthorizationException("no authorization to delete comment");
    }

    commentRepository.delete(comment);
  }
}
