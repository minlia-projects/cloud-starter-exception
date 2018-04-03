package com.minlia.cloud.exception;

/**
 * @author will
 */
public class Assertion {


  public static void is(Boolean expression, Integer code) {
    if (expression) {
      throw new ApiException(code);
    }
  }


  public static void not(Boolean expression, Integer code) {
    is(!expression, code);
  }

  public static <T> T isNull(T reference, Integer code, Object... arguments) {
    if (null == reference) {
      throw new ApiException(code);
    } else {
      return reference;
    }
  }

  public static void throwNewApiException(Integer code) {
    throw new ApiException(code);
  }
}
