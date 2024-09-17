package kr.neoventureholdings.realword_backend.follow.service;

import java.util.NoSuchElementException;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.follow.domains.Follow;
import kr.neoventureholdings.realword_backend.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;
  private final FacadeUserService facadeUserService;

  public User followUser(User user, User targetUser) {
    Follow follow = followRepository.save(Follow
        .builder()
        .follower(user)
        .followee(targetUser)
        .build()
    );

    return follow.getFollower();
  }

  public User unfollowUser(User user, User targetUser) {
    Follow follow = getByFolloweeAndFollower(user, targetUser);

    followRepository.delete(follow);

    return facadeUserService.getRefreshedUser(user);
  }

  private Follow getByFolloweeAndFollower(User user, User targetUser) {
    return followRepository.findByFolloweeAndFollower
        (user, targetUser)
        .orElseThrow(() -> new NoSuchElementException("user currently not following"));
  }
}
