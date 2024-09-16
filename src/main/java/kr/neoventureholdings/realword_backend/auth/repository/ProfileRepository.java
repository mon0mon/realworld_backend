package kr.neoventureholdings.realword_backend.auth.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.auth.domains.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  public Optional<Profile> findById(Long id);
  public Optional<Profile> findByUserId(Long userId);
}
