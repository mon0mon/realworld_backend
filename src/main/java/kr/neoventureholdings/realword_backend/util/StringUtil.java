package kr.neoventureholdings.realword_backend.util;

import kr.neoventureholdings.realword_backend.constant.TokenConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
  public static String replaceAllSpecialCharacter(String value, String replace, boolean isStrip) {
    //  1. 특수문자들을 -로 변환
    String result = value.replaceAll("[^\\w]", replace);

    if (isStrip) {
      //  2. leading과 trailing -을 제거
      //  항상 시작과 끝은 문자나 숫자가 되게끔 지정
      result = result.replaceAll("^[-]+|[-]+$", "");
    }

    return result;
  }

  public static String getToken(String tokenHeader) {
    return tokenHeader.substring(TokenConstant.TOKEN_HEADER_PREFIX.length());
  }
}
