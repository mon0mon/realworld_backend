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

  public ProfileResponseDto getProfile(String username, CustomUserDetail customUserDetail) {
    return profileService.getProfile(username, customUserDetail);
  }

  public ProfileResponseDto followUser(String username, CustomUserDetail customUserDetail) {
    return profileService.followUser(username, customUserDetail);
  }

  public ProfileResponseDto unfollowUser(String username, CustomUserDetail customUserDetail) {
    return profileService.unfollowUser(username, customUserDetail);
  }
}
