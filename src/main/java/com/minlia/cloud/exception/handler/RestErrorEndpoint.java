package com.minlia.cloud.exception.handler;

import com.minlia.cloud.exception.Intrinsics;
import com.minlia.cloud.i18n.Lang;
import com.minlia.cloud.i18n.constant.Constants.LanguageTypes;
import com.minlia.cloud.stateful.body.StatefulBody;
import com.minlia.cloud.stateful.body.impl.FailureResponseBody;
import com.minlia.cloud.i18n.code.ApiCode;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * Based on the helpful answer at http://stackoverflow.com/q/25356781/56285,
 * with error details in response body added.
 * 添加404 JSON输出
 */
@Controller
public class RestErrorEndpoint implements ErrorController {

  private static final String PATH = "/error";


  @Autowired
  private ErrorAttributes errorAttributes;

  //    @RequestMapping(value = PATH)
  @RequestMapping(value = PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  StatefulBody error(HttpServletRequest request, HttpServletResponse response) {
    // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
    // Here we just define response body.
//        return new ErrorJson(response.getStatus(), getErrorAttributes(request, debug));

//    response.setStatus(200);
//    if (!Environments.isProduction()) {
//      return buildBody(request, false);
//    } else {
      return buildBody(request,response, false);
//    }
  }


  private StatefulBody buildBody(HttpServletRequest request,HttpServletResponse response, Boolean includeStackTrace) {
    Map<String, Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
    Integer status = (Integer) errorAttributes.get("status");
    String path = (String) errorAttributes.get("path");
    String messageFound = (String) errorAttributes.get("message");
    Integer code =0;
    try {
      //当可以转换时
       code = Integer.valueOf(messageFound);
    }catch (java.lang.NumberFormatException e){
      //当不可以转换时
      code=ApiCode.NOT_FOUND;
    }

//    Integer messageStatus=extractStatus(messageFound,status);

    response.setStatus(HttpStatus.EXPECTATION_FAILED.value());

    if (HttpStatus.NOT_FOUND.value() != status) {
      String localizedMessage = Lang.get(convertCode(code));
      if (!org.springframework.util.StringUtils.isEmpty(localizedMessage)) {
        messageFound = localizedMessage;
      }
    }else{
      //强制触发抛出异常
      Intrinsics.is(true, ApiCode.NOT_FOUND);
    }

    String message = messageFound;
    String trace = "";
//        if(!StringUtils.isEmpty(path)){
//            message=String.format("Requested path %s with result %s",path,messageFound);
//        }
    if (includeStackTrace) {
      trace = (String) errorAttributes.get("trace");
      if (!StringUtils.isEmpty(trace)) {
        message += String.format(" with trace %s", trace);
      }
    }


    return FailureResponseBody.builder().code(code).status(status).message(message).build();
  }


  private Integer extractStatus(String message,Integer status){
      if(message.startsWith(LanguageTypes.ExceptionsApiCode.name())){
        int index=LanguageTypes.ExceptionsApiCode.name().length();
        String message2=message.substring(index);
        return Integer.valueOf(message2);
      }else{
        return status;
      }

  }
  private String toKeyFormat(String code) {
//    String DOT = ".";
//    code = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, code);
//    code = code.replaceAll("_", DOT);
//    code = code.replaceAll("(\\d+)", ".$1");

    return convertCode(Integer.valueOf(code));
  }

  /**
   *
   * @param code
   * @return
   */
  private  String convertCode(Integer code) {
    String result="";
    if (null!=code) {
      result=  String.format("%s%s%s", "ExceptionsApiCode", getClassForStatic().getSimpleName(), code);
      result = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(result), ".").toLowerCase();
    }
    return result;
  }


  private static final Class<?> getClassForStatic() {
    return new Object() {
      public Class<?> getClassForStatic() {
        return this.getClass();
      }
    }.getClassForStatic();
  }



  @Override
  public String getErrorPath() {
    return PATH;
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest request,
      boolean includeStackTrace) {
    //FOR 2.0.0.RELEASE CHANGES
//    RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    ServletWebRequest requestAttributes = new ServletWebRequest(request);
    return errorAttributes.getErrorAttributes((WebRequest) requestAttributes, includeStackTrace);
  }

}