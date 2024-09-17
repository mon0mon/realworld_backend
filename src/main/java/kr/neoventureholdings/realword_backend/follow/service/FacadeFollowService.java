package kr.neoventureholdings.realword_backend.follow.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeFollowService {
  private final FollowService followService;

  @Transactional
  public User followUser(User user, User targetUser) {
    return followService.followUser(user, targetUser);
  }

  @Transactional
  public User unfollowUser(User user, User targetUser) {
    return followService.unfollowUser(user, targetUser);
  }
}
