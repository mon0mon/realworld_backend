package kr.neoventureholdings.realword_backend.util;

import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
  public static CustomUserDetail getCurrentUserCustomUserdetail() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("context에 정보가 없습니다.");
      return null;
    }

    return (CustomUserDetail) authentication.getPrincipal();
  }

  public static Long getCurrentUserId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("context에 정보가 없습니다.");
      return null;
    }

    CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

    return userDetail.getId();
  }

  public static String getCurrentToken() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("context에 정보가 없습니다.");
      return null;
    }

    CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

    return userDetail.getToken();
  }
}
