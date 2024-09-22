package kr.neoventureholdings.realword_backend.tag.repository;

import kr.neoventureholdings.realword_backend.tag.domains.TagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapRepository extends JpaRepository<TagMap, Long> {
}
