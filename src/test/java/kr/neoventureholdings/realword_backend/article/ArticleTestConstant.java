package kr.neoventureholdings.realword_backend.article;

import java.util.List;

public class ArticleTestConstant {
  public static final String DEFAULT_AUTHOR = "celeb";
  public static final String DEFAULT_AUTHOR2 = "user1";
  public static final String DEFAULT_FAVORITED = "user1";
  public static final String DEFAULT_TAG = "dragons";
  public static final String DEFAULT_SLUG = "how-to-train-your-dragon";
  public static final String CREATE_SLUG = "how-to-train-your-dragon";
  public static final String CREATE_TITLE = "How to train your dragon";
  public static final String CREATE_DESCRIPTION = "Ever wonder how?";
  public static final String CREATE_BODY = "Very carefully.";
  public static final List<String> CREATE_TAGLIST = List.of("training", "dragons");
  public static final String UPDATE_TARGET_SLUG = "javascript";
  public static final String UPDATE_TITLE = "TEST...SPRING";
  public static final String UPDATE_DESCRIPTION = "IS_TEST";
  public static final String UPDATE_BODY = "With two hands";
  public static final List<String> UPDATE_TAGlIST = List.of("training", "dragon");
  public static final String UPDATE_SLUG = "test---spring";
  public static final String UPDATE_EXCEPTION_UNIQUEVIOLATION_TITLE = "Node.js";
  public static final String UPDATE_EXCEPTION_NOAUTHORIZATION_TARGET_SLUG = "spring";
  public static final String DELETE_TARGET_SLUG = "javascript";
  public static final String DELETE_EXCEPTION_NOAUTHORIZATION_TARGET_SLUG = "spring";
  public static final String PREDEFINED_RESULT_TITLE = "Node.js";
  public static final String PREDEFINED_RESULT_DESCRIPTION = "Node.js";
  public static final String PREDEFINED_RESULT_BODY = "Node.js is a cross-platform, open-source JavaScript runtime environment.";
  public static final String PREDEFINED_RESULT_SLUG = "node-js";
  public static final String PREDEFINED_RESULT_AUTHOR_USERNAME = "celeb";
}
