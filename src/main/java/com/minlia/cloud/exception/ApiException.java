package com.minlia.cloud.exception;

import com.minlia.cloud.stateful.body.StatefulBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author user
 * @date 2/24/17
 */
@ResponseStatus(value = HttpStatus.OK)
public class ApiException extends NestedRuntimeException {

  private Integer code;
  private int status;
  private Boolean translateRequired;
  private Object[] arguments;


  /**
   * 使用自定义的消息内容返回，不需要国际化
   * @param msg
   */
  public ApiException(String msg) {
    super(msg);
    this.status =HttpStatus.OK.value();
    this.code = StatefulBody.FAILURE;
    this.translateRequired = Boolean.FALSE;
    this.arguments = new Object[]{};
  }

  /**
   * 使用ApiCode格式化消息后返回，需要国际化
   * @param code
   */
  public ApiException(Integer code) {
    super(code+"");
    this.code = code;
    this.status = HttpStatus.OK.value();
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};
  }

  /**
   * 将http状态也返回出去，需要国际化
   */
  public ApiException(Integer code, int status) {
//    super(convertCode(String.format("%s%s%s", "ExceptionsApiCode", getClassForStatic().getSimpleName(), code)));
    super(code+"");
    this.code = code;
    this.status = status;
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};
  }

//  public ApiException(int code, String msg) {
//    super(msg);
//    this.code = code;
//    this.status = StatefulBody.FAILURE;
//    this.translateRequired = Boolean.FALSE;
//    this.arguments = new Object[]{};
//  }
//
//
//  public ApiException(int code, String msg, Boolean translateRequired) {
//    super(msg);
//    this.code = code;
//    this.status = StatefulBody.FAILURE;
//    this.translateRequired = translateRequired;
//    this.arguments = new Object[]{};
//  }
//
//
//  public ApiException(int code, String msg, Boolean translateRequired, Object... arguments) {
//    super(msg);
//    this.code = code;
//    this.status = StatefulBody.FAILURE;
//    this.translateRequired = translateRequired;
//    this.arguments = arguments;
//  }
//
//  /**
//   * 添加状态码
//   */
//  public ApiException(int code, int status, String msg, Boolean translateRequired,
//      Object... arguments) {
//    super(msg);
//    this.code = code;
//    this.status = status;
//    this.translateRequired = translateRequired;
//    this.arguments = arguments;
//  }

  public ApiException() {
    super(String.format("%s%s", "ExceptionsApiCode", getClassForStatic().getSimpleName()));
  }

  public ApiException(String msg, Throwable cause) {
    super(msg, cause);
  }

  private static final Class<?> getClassForStatic() {
    return new Object() {
      public Class<?> getClassForStatic() {
        return this.getClass();
      }
    }.getClassForStatic();
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Boolean getTranslateRequired() {
    return translateRequired;
  }

  public void setTranslateRequired(Boolean translateRequired) {
    this.translateRequired = translateRequired;
  }

  public Object[] getArguments() {
    return arguments;
  }

  public void setArguments(Object[] arguments) {
    this.arguments = arguments;
  }
}
