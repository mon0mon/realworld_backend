package kr.neoventureholdings.realword_backend.follow.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeFollowService {
  private final FollowService followService;

  public User followUser(User user, User targetUser) {
    return followService.followUser(user, targetUser);
  }

  public User unfollowUser(User user, User targetUser) {
    return followService.unfollowUser(user, targetUser);
  }
}
