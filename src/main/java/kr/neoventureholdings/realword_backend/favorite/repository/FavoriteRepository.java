package kr.neoventureholdings.realword_backend.favorite.repository;

import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
