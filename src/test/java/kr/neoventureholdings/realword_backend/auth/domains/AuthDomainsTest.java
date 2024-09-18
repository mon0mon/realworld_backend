package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import kr.neoventureholdings.realword_backend.auth.AuthTestConstant;
import kr.neoventureholdings.realword_backend.profile.domains.Profile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthDomainsTest {

  private static Validator validator;

  @BeforeAll
  public static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("User Validation 테스트 1 - pass")
  void UserValidationTest() {
    User user = User.builder()
        .email(AuthTestConstant.EMAIL)
        .password(AuthTestConstant.PASSWORD)
        .profile(Profile
            .builder()
            .username(AuthTestConstant.USERNAME)
            .build())
        .build();

    Set<ConstraintViolation<User>> validations = validator.validate(user);
    Assertions.assertThat(validations)
        .isEmpty();
  }

  @Test
  @DisplayName("User Validation 테스트 2 - email")
  void UserValidationTest2() {
    User user = User.builder()
        .email("isNotAnEmail")
        .password("password")
        .build();

    Set<ConstraintViolation<User>> validations = validator.validate(user);
    Assertions.assertThat(
        validations
            .stream()
            .filter(validate -> validate.getMessageTemplate().equals("{jakarta.validation.constraints.Email.message}"))
            .count())
        .isEqualTo(1);
  }

  @Test
  @DisplayName("User Validation 테스트 3 - password")
  void UserValidationTest4() {
    User user = User.builder()
        .email("test@example.com")
        .password("")
        .build();

    Set<ConstraintViolation<User>> validations = validator.validate(user);
    Assertions.assertThat(
            validations
                .stream()
                .filter(validate -> validate.getMessageTemplate().equals("{jakarta.validation.constraints.NotEmpty.message}"))
                .count())
        .isEqualTo(1);
  }
}
