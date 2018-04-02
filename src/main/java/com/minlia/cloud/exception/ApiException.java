package com.minlia.cloud.exception;

import com.minlia.cloud.stateful.body.StatefulBody;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author user
 * @date 2/24/17
 */
@ResponseStatus(value = HttpStatus.OK)
public class ApiException extends NestedRuntimeException {

  private int code;
  private int status;
  private Boolean translateRequired;
  private Object[] arguments;

  public ApiException(String msg) {
    super(msg);
    this.status = StatefulBody.FAILURE;
    this.code = StatefulBody.FAILURE;
    this.translateRequired = Boolean.FALSE;
    this.arguments = new Object[]{};
  }

  public ApiException(int code) {
    super(String.format("%s%s%s", "ExceptionsApiCode", getClassForStatic().getSimpleName(), code));
    this.code = code;
    this.status = StatefulBody.FAILURE;
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};

  }

  /**
   * 将http状态也返回出去
   */
  public ApiException(int code, int status) {
    super(String.format("%s%s%s", "ExceptionsApiCode", getClassForStatic().getSimpleName(), code));
    this.code = code;
    this.status = status;
    this.translateRequired = Boolean.TRUE;
    this.arguments = new Object[]{};
  }

  public ApiException(int code, String msg) {
//        super(String.format("%s%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName(),code));
//        super(String.format("%s%s","Exceptions",getClassForStatic().getSimpleName()));
    super(msg);
    this.code = code;
    this.status = StatefulBody.FAILURE;
    this.translateRequired = Boolean.FALSE;
    this.arguments = new Object[]{};
  }


  public ApiException(int code, String msg, Boolean translateRequired) {
//        super(String.format("%s%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName(),code));
//        super(String.format("%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName()));
    super(msg);
    this.code = code;
    this.status = StatefulBody.FAILURE;
    this.translateRequired = translateRequired;
    this.arguments = new Object[]{};
  }


  public ApiException(int code, String msg, Boolean translateRequired, Object... arguments) {
//        super(String.format("%s%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName(),code));
//        super(String.format("%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName()));
    super(msg);
    this.code = code;
    this.status = StatefulBody.FAILURE;
    this.translateRequired = translateRequired;
    this.arguments = arguments;
  }

  /**
   * 添加状态码
   */
  public ApiException(int code, int status, String msg, Boolean translateRequired,
      Object... arguments) {
//        super(String.format("%s%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName(),code));
//        super(String.format("%s%s","ExceptionsApiCode",getClassForStatic().getSimpleName()));
    super(msg);
    this.code = code;
    this.status = status;
    this.translateRequired = translateRequired;
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
