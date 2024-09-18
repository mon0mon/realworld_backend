package kr.neoventureholdings.realword_backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.neoventureholdings.realword_backend.auth.AuthTestConstant;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.common.dto.CommonRequestDto;

public class AuthTestUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static CommonRequestDto getLoginDefaultUserRequest() {
    return getLoginUserRequest(AuthTestConstant.EMAIL, AuthTestConstant.PASSWORD);
  }

  public static CommonRequestDto getLoginUpdatedUserRequest() {
    return getLoginUserRequest(AuthTestConstant.UPDATE_EMAIL, AuthTestConstant.UPDATE_PASSWORD);
  }

  public static CommonRequestDto getLoginUserRequest(String email, String password) {
    return getCommonRequestDto(email, password);
  }

  public static CommonRequestDto getRegisterUserRequest() {
    return getRegisterUserRequest(AuthTestConstant.REGISTER_EMAIL, AuthTestConstant.REGISTER_USERNAME,
        AuthTestConstant.REGISTER_PASSWORD);
  }

  public static CommonRequestDto getRegisterUserRequest(String email, String username,
      String password) {
    return getCommonRequestDto(email, username, password);
  }

  public static CommonRequestDto getUpdateUserRequest() {
    return getCommonRequestDto(AuthTestConstant.UPDATE_EMAIL, AuthTestConstant.UPDATE_USERNAME, AuthTestConstant.UPDATE_PASSWORD,
        AuthTestConstant.UPDATE_BIO, AuthTestConstant.UPDATE_IMAGE);
  }

  public static CommonRequestDto getUpdateUserRequest(String email, String username,
      String password, String bio, String image) {
    return getCommonRequestDto(email, username, password, bio, image);
  }

  private static CommonRequestDto getCommonRequestDto(String email, String password) {
    return getCommonRequestDto(email, null, password, null, null);
  }

  private static CommonRequestDto getCommonRequestDto(String email, String username, String password) {
    return getCommonRequestDto(email, username, password, null, null);
  }

  private static CommonRequestDto getCommonRequestDto(String email, String username,
      String password, String bio, String image) {
    UserRequestDto userRequestDto = UserRequestDto.builder()
        .email(email)
        .username(username)
        .password(password)
        .bio(bio)
        .image(image)
        .build();

    return CommonRequestDto.builder()
        .user(userRequestDto)
        .build();
  }

  public static String getResponseToken(String response) throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(response);
    return jsonNode.path("user")
        .path("token")
        .asText();
  }
}
