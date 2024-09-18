package kr.neoventureholdings.realword_backend.util;

import kr.neoventureholdings.realword_backend.config.security.authentication.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
  private SecurityUtil() {
  }

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

  public static String getCurrentUserEmail() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("context에 정보가 없습니다.");
      return null;
    }

    CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

    return userDetail.getEmail();
  }

  public static String getCurrentUserUsername() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("context에 정보가 없습니다.");
      return null;
    }

    CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

    return userDetail.getUsername();
  }
}
