package kr.neoventureholdings.realword_backend.follow.repository;

import java.util.Optional;
import java.util.Set;
import kr.neoventureholdings.realword_backend.follow.domains.Follow;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
  public Optional<Follow> findById(Long id);
  public Set<Follow> findFollowsByFollower(User follower);
  public Set<Follow> findFollowsByFollowee(User Followee);
}
