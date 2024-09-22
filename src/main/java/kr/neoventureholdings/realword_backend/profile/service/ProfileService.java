package kr.neoventureholdings.realword_backend.profile.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.exception.auth.NoAuthorizationException;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
import kr.neoventureholdings.realword_backend.follow.service.FacadeFollowService;
import kr.neoventureholdings.realword_backend.profile.domains.Profile;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
import kr.neoventureholdings.realword_backend.profile.repository.ProfileRepository;
import kr.neoventureholdings.realword_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final FacadeUserService facadeUserService;
  private final FacadeFollowService facadeFollowService;

  public ProfileResponseDto getProfile(String username) {
    Profile profile = getProfileByUsername(username);

    CustomUserDetail userDetail = SecurityUtil.getCurrentUserCustomUserdetail();

    if (userDetail == null || userDetail.isAnonymous()) {
      return profile.of(null);
    }

    User user = facadeUserService.getCurrentUser(userDetail);

    return profile.of(user);
  }

  public ProfileResponseDto followUser(String followeeUsername) {
    CustomUserDetail userDetail = SecurityUtil.getCurrentUserCustomUserdetail();

    if (userDetail == null || userDetail.isAnonymous()) {
      throw new NoAuthorizationException("to follow user, you need to be logged in");
    }

    Profile profile = getProfileByUsername(followeeUsername);
    User targetUser = profile.getUser();

    User user = facadeUserService.getCurrentUser(userDetail);

    if (isFollowing(user, targetUser)) {
      return profile.of(user);
    }

    user = followGivenUser(user, targetUser);

    return profile.of(user);
  }

  public ProfileResponseDto unfollowUser(String followeeUsername) {
    CustomUserDetail userDetail = SecurityUtil.getCurrentUserCustomUserdetail();

    if (userDetail == null || userDetail.isAnonymous()) {
      throw new NoAuthorizationException("to unfollow user, you need to be logged in");
    }

    Profile profile = getProfileByUsername(followeeUsername);
    User targetUser = profile.getUser();

    User user = facadeUserService.getCurrentUser(userDetail);

    if (!isFollowing(user, targetUser)) {
      return profile.of(user);
    }

    user = unfollowGivenUser(user, targetUser);

    return profile.of(user);
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

  private User followGivenUser(User user, User targetUser) {
    return facadeFollowService.followUser(user, targetUser);
  }

  private User unfollowGivenUser(User user, User targetUser) {
    return facadeFollowService.unfollowUser(user, targetUser);
  }
}
