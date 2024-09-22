package kr.neoventureholdings.realword_backend.profile.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.profile.domains.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  public Optional<Profile> findById(Long id);
  @EntityGraph(attributePaths = {"user"})
  public Optional<Profile> findByUsername(String username);
}
