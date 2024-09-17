package kr.neoventureholdings.realword_backend.profile.service;

import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.profile.domains.Profile;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
import kr.neoventureholdings.realword_backend.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final FacadeUserService facadeUserService;

  public ProfileResponseDto getProfile(String username, CustomUserDetail customUserDetail) {
    Profile profile = getProfileByUsername(username);

    if (customUserDetail == null || customUserDetail.isAnonymous()) {
      return profile.of(null);
    }

    User user = facadeUserService.getCurrentUser(customUserDetail);

    return profile.of(user);
  }

  public ProfileResponseDto followUser(String username, CustomUserDetail customUserDetail) {
    assert customUserDetail != null;

    Profile profile = getProfileByUsername(username);

    User user = facadeUserService.getCurrentUser(customUserDetail);

    return profile.of(user);
  }

  public ProfileResponseDto unfollowUser(String username, CustomUserDetail customUserDetail) {
    assert customUserDetail != null;

    Profile profile = getProfileByUsername(username);

    //  TODO Follow 기능이 추가될 때 마저 작성

    User user = facadeUserService.getCurrentUser(customUserDetail);

    return profile.of(user);
  }

  private Profile getProfileByUsername(String username) {
    return profileRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No Profile Found"));
  }
}
