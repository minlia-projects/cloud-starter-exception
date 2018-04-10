package com.minlia.cloud.exception.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.minlia.cloud.exception.ApiException;
import com.minlia.cloud.exception.ExceptionConverter;
import com.minlia.cloud.exception.HttpResponse;
import com.minlia.cloud.exception.v1.ErrorConstants;
import com.minlia.cloud.exception.v1.FieldErrorVM;
import com.minlia.cloud.i18n.Lang;
import com.minlia.cloud.stateful.body.impl.FailureResponseBody;
import com.minlia.cloud.stateful.code.ApiCode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;

/**
 * @author will
 */
@RestControllerAdvice
public class ApiExceptionHandler  implements ProblemHandling {

  private Logger log = LoggerFactory.getLogger(Exception.class);

  /**
   * ApiException顶级捕获处理
   */
  @ExceptionHandler(value = {ApiException.class})
  public void processApiException(ApiException e, HttpServletResponse response) {
    log.error(e.getMessage(), e);
    FailureResponseBody body = new FailureResponseBody();
    body.setCode(e.getCode());
    body.setStatus(e.getStatus());
    body.setMessage(translateMessage(e));
    log.debug("Response out: {}",
        JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
    if(e.getStatus()>0 ){
      HttpResponse.outJson(response,e.getStatus(), body);
    }else {
      HttpResponse.outJson(response, body);
    }
  }

  /**
   * @param e
   * @param response
   */
  @ExceptionHandler(value = {Exception.class })
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
    body.setMessage(e.getMessage());
    log.debug("Response out: {}",
        JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
    HttpResponse.outJson(response, HttpStatus.OK.value(), body);
  }


  private String translateMessage(ApiException e) {
    if(e.getTranslateRequired()){
      //需要翻译
      String code= ExceptionConverter.convert(e.getCode());
      return Lang.get(code,e.getArguments());
    }else {
      return e.getMessage();
    }
  }


  /**
   * Post-process Problem payload to add the message key for front-end if needed
   */
  @Override
  public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
    if (entity == null || entity.getBody() == null) {
      return entity;
    }
    Problem problem = entity.getBody();
    if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
      return entity;
    }
    ProblemBuilder builder = Problem.builder()
        .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
        .withStatus(problem.getStatus())
        .withTitle(problem.getTitle())
        .with("path", request.getNativeRequest(HttpServletRequest.class).getRequestURI());

    if (problem instanceof ConstraintViolationProblem) {
      builder
          .with("violations", ((ConstraintViolationProblem) problem).getViolations())
          .with("message", ErrorConstants.ERR_VALIDATION);
      return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    } else {
      builder
          .withCause(((DefaultProblem) problem).getCause())
          .withDetail(problem.getDetail())
          .withInstance(problem.getInstance());
      problem.getParameters().forEach(builder::with);
      if (!problem.getParameters().containsKey("message") && problem.getStatus() != null) {
        builder.with("message", "error.http." + problem.getStatus().getStatusCode());
      }
      return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }
  }

  @Override
  public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
    BindingResult result = ex.getBindingResult();
    List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
        .map(f -> new FieldErrorVM(f.getObjectName(), f.getField(), f.getCode()))
        .collect(Collectors.toList());

    Problem problem = Problem.builder()
        .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
        .withTitle("Method argument not valid")
        .withStatus(defaultConstraintViolationStatus())
        .with("message", ErrorConstants.ERR_VALIDATION)
        .with("fieldErrors", fieldErrors)
        .build();
    return create(ex, problem, request);
  }

//    @ExceptionHandler(BadRequestAlertException.class)
//    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
//        return create(ex, request, HeaderUtil.createFailureAlert(ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
//    }
//
//    @ExceptionHandler(ConcurrencyFailureException.class)
//    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
//        Problem problem = Problem.builder()
//            .withStatus(Status.CONFLICT)
//            .with("message", ErrorConstants.ERR_CONCURRENCY_FAILURE)
//            .build();
//        return create(ex, problem, request);
//    }
}
