package kr.neoventureholdings.realword_backend.favorite.service;

import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import kr.neoventureholdings.realword_backend.favorite.dto.FavoriteDto;
import kr.neoventureholdings.realword_backend.favorite.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {
  private final FavoriteRepository favoriteRepository;

  @Transactional
  public Favorite saveFavorite(FavoriteDto dto) {
    return favoriteRepository.save(Favorite.of(dto));
  }

  @Transactional
  public void deleteFavorite(Favorite favorite) {
    favoriteRepository.delete(favorite);
  }
}
