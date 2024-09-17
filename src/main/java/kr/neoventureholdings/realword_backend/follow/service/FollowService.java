package kr.neoventureholdings.realword_backend.follow.service;

import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.auth.service.FacadeUserService;
import kr.neoventureholdings.realword_backend.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;
  private final FacadeUserService facadeUserService;

  @Transactional
  public User followUser(User user, User targetUser) {
    user.addFollowee(targetUser);

    return user;
  }

  @Transactional
  public User unfollowUser(User user, User targetUser) {
    user.removeFollowee(targetUser);

    return user;
  }
}
