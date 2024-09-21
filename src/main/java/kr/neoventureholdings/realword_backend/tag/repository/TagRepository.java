package kr.neoventureholdings.realword_backend.tag.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
  public Optional<Tag> findByValue (String value);
}
