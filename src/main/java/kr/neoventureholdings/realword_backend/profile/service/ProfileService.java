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

  public ProfileResponseDto followUser(String followeeUsername, CustomUserDetail customUserDetail) {
    assert customUserDetail != null;

    Profile profile = getProfileByUsername(followeeUsername);
    User targetUser = profile.getUser();

    User user = facadeUserService.getCurrentUser(customUserDetail);

    if (isFollowing(user, targetUser)) {
      return profile.of(user);
    }

    return profile.of(user);
  }

  public ProfileResponseDto unfollowUser(String followeeUsername, CustomUserDetail customUserDetail) {
    assert customUserDetail != null;

    Profile profile = getProfileByUsername(followeeUsername);

    User user = facadeUserService.getCurrentUser(customUserDetail);

    return profile.of(user);
  }

  private boolean isFollowing(User user, String followeeUsername) {
    return isFollowing(user, getProfileByUsername(followeeUsername).getUser());
  }

  private boolean isFollowing(User user, User targetUser) {
    return user.getFollowees()
        .stream()
        .anyMatch(followee ->
            followee.equals(targetUser));
  }

  private Profile getProfileByUsername(String username) {
    return profileRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No Profile Found"));
  }

//  private User followGivenUsername(String followeeUsername, String followerUsername) {
//    return
//  }
}
