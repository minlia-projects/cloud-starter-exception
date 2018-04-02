package com.minlia.cloud.exception.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.minlia.cloud.context.Lang;
import com.minlia.cloud.exception.ApiException;
import com.minlia.cloud.exception.HttpResponse;
import com.minlia.cloud.stateful.body.impl.FailureResponseBody;
import com.minlia.cloud.stateful.code.ApiCode;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author will
 */
@RestControllerAdvice
public class ApiExceptionHandler {

  private Logger log = LoggerFactory.getLogger(Exception.class);

  /**
   * ApiException顶级捕获处理
   */
  @ExceptionHandler(value = {ApiException.class})
  public void processHttpException(ApiException e, HttpServletResponse response) {
    log.error(e.getMessage(), e);
    FailureResponseBody body = new FailureResponseBody();
    body.setCode(e.getCode());
    body.setMessage(translateMessage(e.getMessage(), e.getArguments()));
    log.debug("Response out: {}",
        JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
    HttpResponse.outJson(response, body);
  }

  /**
   * @param e
   * @param response
   */
  @ExceptionHandler(value = {Exception.class})
  public void processException(Exception e, HttpServletResponse response) {
    log.error(e.getMessage(), e);
    FailureResponseBody body = new FailureResponseBody();
    // 设置默认异常编码
    body.setCode(ApiCode.UNKNOWN_EXCEPTION);
    // JDK异常捕获处理
    if (e instanceof NullPointerException) {
      body.setCode(ApiCode.NULL_POINTER_EXCEPTION);
    } else if (e instanceof FileNotFoundException) {
      body.setCode(ApiCode.FILE_NOT_FOUND_EXCEPTION);
    } else if (e instanceof IOException) {
      body.setCode(ApiCode.IO_EXCEPTION);
    } else {
      body.setCode(ApiCode.EXCEPTION);
    }
    body.setMessage(translateMessage(e.getMessage()));
    log.debug("Response out: {}",
        JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
    HttpResponse.outJson(response, body);
  }


  private String translateMessage(String key, Object... arguments) {
    return Lang.get(key, arguments);
  }


}
