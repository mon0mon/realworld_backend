package kr.neoventureholdings.realword_backend.follow.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.follow.domains.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
  public Optional<Follow> findById(Long id);
}
