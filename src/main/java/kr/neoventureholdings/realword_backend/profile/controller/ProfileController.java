package kr.neoventureholdings.realword_backend.profile.controller;

import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import kr.neoventureholdings.realword_backend.profile.service.FacadeProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

  private final FacadeProfileService profileService;

  /**
   * 주어진 username에 해당하는 이용자의 프로필을 조회
   *
   * @param username
   * @return
   */
  @GetMapping("/{username}")
  public ResponseEntity<CommonResponseDto> getProfile(@PathVariable("username") String username) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .profileResponseDto(profileService.getProfile(username))
            .build()
        );
  }

  /**
   * 주어진 username에 해당하는 이용자를 follow
   *
   * @param username
   * @return
   */
  @PostMapping("/{username}/follow")
  public ResponseEntity<CommonResponseDto> followProfile(@PathVariable("username") String username) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .profileResponseDto(profileService.followUser(username))
            .build()
        );
  }

  /**
   * 주어진 username에 해당하는 이용자를 unfollow
   *
   * @param username
   * @return
   */
  @DeleteMapping("/{username}/follow")
  public ResponseEntity<CommonResponseDto> unfollowProfile(
      @PathVariable("username") String username) {
    return ResponseEntity
        .ok()
        .body(CommonResponseDto.builder()
            .profileResponseDto(profileService.unfollowUser(username))
            .build()
        );
  }
}
