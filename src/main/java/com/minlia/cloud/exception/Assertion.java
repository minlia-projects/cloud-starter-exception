package com.minlia.cloud.exception;

import com.google.common.base.Preconditions;
import com.minlia.cloud.stateful.code.ApiCode;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

/**
 * @author will
 */
public class Assertion {


  /**
   * TODO: 有参数传入国际化时的处理
   * @param expression
   * @param code
   */
  public static void is(Boolean expression, Integer code) {
    if(expression) {
      throw new ApiException(code);
    }
  }


  /**
   * Helper of is
   * @param expression
   * @param code
   */
  public static void  not(Boolean expression, Integer code) {
      is(!expression, code);
  }

  /**
   * 检查对象是否为空，当为空时抛出 CODE 的异常
   * @param reference
   * @param code
   * @param arguments
   * @param <T>
   * @return
   */
  public static <T> T isNull(T reference,Integer code,Object... arguments) {
    if (null==reference ) {
      throw new ApiException(code);
    } else {
      return reference;
    }
  }

  public static void throwNewApiException(Integer code){
    throw new ApiException(code);
  }
}
