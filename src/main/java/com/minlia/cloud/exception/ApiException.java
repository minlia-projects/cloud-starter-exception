package com.minlia.cloud.exception;

import com.minlia.cloud.stateful.body.StatefulBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author user
 */
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class ApiException extends NestedRuntimeException {

  private Integer code;
  private int status;
  private Boolean translateRequired;
  private Object[] arguments;


  /**
   * 使用自定义的消息内容返回，不需要国际化，不需要参数传入
   */
  public ApiException(String msg) {
    super(msg);
    this.status =HttpStatus.EXPECTATION_FAILED.value();
    this.code = StatefulBody.FAILURE;
    this.translateRequired = Boolean.FALSE;
    this.arguments = new Object[]{};
  }

  /**
   * 使用ApiCode格式化消息后返回，需要国际化，不需要参数传入
   */
  public ApiException(Integer code) {
    super(code+"");
    this.code = code;
    this.status = HttpStatus.EXPECTATION_FAILED.value();
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};
  }

  /**
   * 使用ApiCode格式化消息后返回，需要国际化，需要参数传入
   * @param code
   * @param arguments
   */
  public ApiException(Integer code,Object ... arguments) {
    super(code+"");
    this.code = code;
    this.status = HttpStatus.EXPECTATION_FAILED.value();
    this.translateRequired = Boolean.TRUE;
    this.arguments = arguments;
  }


  /**
   * 将http状态也返回出去，需要国际化，不需要参数传入
   */
  public ApiException(Integer code, int status) {
    super(code+"");
    this.code = code;
    this.status = status;
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};
  }

  /**
   * 将http状态也返回出去，需要国际化，需要传入参数
   * @param code
   * @param status
   * @param arguments
   */
  public ApiException(Integer code, int status,Object ... arguments) {
    super(code+"");
    this.code = code;
    this.status = status;
    this.translateRequired = Boolean.TRUE;
    this.arguments = arguments;
  }

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
