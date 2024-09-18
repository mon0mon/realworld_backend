package kr.neoventureholdings.realword_backend.favorite.service;

import kr.neoventureholdings.realword_backend.favorite.domains.Favorite;
import kr.neoventureholdings.realword_backend.favorite.dto.FavoriteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeFavoriteService {
  private final FavoriteService favoriteService;

  public Favorite saveFavorite(FavoriteDto dto) {
    return favoriteService.saveFavorite(dto);
  }

  public void deleteFavorite(FavoriteDto dto) {
    favoriteService.deleteFavorite(dto);
  }

  public void deleteFavorite(Favorite favorite) {
    favoriteService.deleteFavorite(favorite);
  }
}
