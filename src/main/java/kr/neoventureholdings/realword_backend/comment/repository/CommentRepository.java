package kr.neoventureholdings.realword_backend.comment.repository;

import kr.neoventureholdings.realword_backend.comment.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
