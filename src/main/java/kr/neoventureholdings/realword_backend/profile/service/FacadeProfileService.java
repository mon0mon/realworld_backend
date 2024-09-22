package kr.neoventureholdings.realword_backend.profile.service;

import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeProfileService {
  private final ProfileService profileService;

  public ProfileResponseDto getProfile(String username) {
    return profileService.getProfile(username);
  }

  public ProfileResponseDto followUser(String username) {
    return profileService.followUser(username);
  }

  public ProfileResponseDto unfollowUser(String username) {
    return profileService.unfollowUser(username);
  }
}
