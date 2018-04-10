package com.minlia.cloud.exception;

import com.minlia.cloud.context.EnvironmentHolder;
import org.springframework.http.HttpStatus;

/**
 * @author will
 */
public class Intrinsics {

  /**
   * 检查参数是否为空，为空则抛出异常
   */
  public static void isNull(Object parameter, Object code, Object... arguments) {
    if (null == parameter) {
      throwExceptionWithCodeAndArguments(code, arguments);
    } else {
      throwException("[DevelopmentPhaseError]传入的参数${parameter}不能为空");
    }
  }

  public static void isNull(Object parameter, Object code,HttpStatus status, Object... arguments) {
    if (null == parameter) {
      throwExceptionWithCodeAndArguments(code,status, arguments);
    } else {
      throwException("[DevelopmentPhaseError]传入的参数${parameter}不能为空");
    }
  }

  public static void isNotNull(Object parameter, Object code, Object... arguments) {
    if (null != parameter) {
      throwExceptionWithCodeAndArguments(code, arguments);
    } else {
      throwException("[DevelopmentPhaseError]传入的参数${parameter}不能为空");
    }
  }
  public static void isNotNull(Object parameter, Object code,HttpStatus status, Object... arguments) {
    if (null != parameter) {
      throwExceptionWithCodeAndArguments(code,status, arguments);
    } else {
      throwException("[DevelopmentPhaseError]传入的参数${parameter}不能为空");
    }
  }

  /**
   * 从系统环境上下文中取出表达式的值${spring.name}，传入 "spring.name" ，检查表达式的值是否为空，为空则抛出异常
   */
  public static void isNull(String parameter, Object code, Object... arguments) {
    String value = EnvironmentHolder.getEnvironment().getProperty(parameter.toString());
    if (null == value) {
      isNull(parameter, code, arguments);
    }
  }
  public static void isNull(String parameter, Object code,HttpStatus status, Object... arguments) {
    String value = EnvironmentHolder.getEnvironment().getProperty(parameter.toString());
    if (null == value) {
      isNull(parameter, code, status,arguments);
    }
  }


  /**
   * 从系统环境上下文中取出表达式的值${spring.name}，传入 "spring.name" ，检查表达式的值是否为空，为空则抛出异常
   */
  public static void isNotNull(String parameter, Object code, Object... arguments) {
    String value = EnvironmentHolder.getEnvironment().getProperty(parameter.toString());
    if (null != value) {
      isNotNull(parameter, code, arguments);
    }
  }
  public static void isNotNull(String parameter, Object code,HttpStatus status, Object... arguments) {
    String value = EnvironmentHolder.getEnvironment().getProperty(parameter.toString());
    if (null != value) {
      isNotNull(parameter, code, status,arguments);
    }
  }

  /**
   * 表达式是否为真，当为真时抛出异常
   */
  public static void is(Boolean expression, Object code, Object... arguments) {
    if (expression) {
      throwExceptionWithCodeAndArguments(code, arguments);
    }
  }
  public static void is(Boolean expression, Object code,HttpStatus status, Object... arguments) {
    if (expression) {
      throwExceptionWithCodeAndArguments(code, status,arguments);
    }
  }

  /**
   * 表达式是否为假，当为假时抛出异常
   */
  public static void not(Boolean expression, Object code, Object... arguments) {
    is(!expression, code, arguments);
  }

  public static void not(Boolean expression, Object code,HttpStatus status, Object... arguments) {
    is(!expression, code,status, arguments);
  }


  private static void throwExceptionWithCodeAndArguments(Object code,  Object... arguments) {
    throwExceptionWithCodeAndArguments(code,HttpStatus.OK,arguments);
  }

  private static void throwExceptionWithCodeAndArguments(Object code,HttpStatus status, Object... arguments) {
    //当code为Integer时才可能有参数
    if (null == arguments) {
      //为防止没有传入时出现异常
      arguments = new Object[]{};
    }
    //处理code为String的情况
    if (code instanceof String) {
      throw new ApiException(code.toString());
    } else if (code instanceof Integer) {
      throwException(Integer.valueOf(code.toString()),status.value(), arguments);
    } else {
      throwException("[DevelopmentPhaseError]不可以传入非字符串或整型的code参数");
    }
  }






  /**
   * 抛出异常
   */
  public static void throwException(Integer code, Object... arguments) {
    throw new ApiException(code, arguments);
  }

  /**
   * 抛出带错误状态的异常
   * @param code
   * @param httpStatus
   * @param arguments
   */
  public static void throwException(Integer code,Integer httpStatus, Object... arguments) {
    throw new ApiException(code,httpStatus, arguments);
  }

  /**
   * 抛出异常
   */
  public static void throwException(String code) {
    throw new ApiException(code);
  }





}
