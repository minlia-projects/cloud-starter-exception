package com.minlia.cloud.exception;

import com.alibaba.fastjson.JSON;
import com.minlia.cloud.stateful.body.StatefulBody;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

/**
 * Response工具类
 *
 * @author lindp
 * @author will
 * @date 2015/3/27
 */
public class HttpResponse {

  /**
   * 输出文本格式到客户端
   *
   * @param str 文本字符串
   */
  public static void outText(HttpServletResponse response, String str) {
    out(response, MediaType.TEXT_PLAIN_VALUE, str);
  }

  /**
   * 输出json格式到客户端
   *
   * @param data json格式字符串
   */
  public static void outJson(HttpServletResponse response, String data) {
    out(response, MediaType.APPLICATION_JSON_UTF8_VALUE, data);
  }

  /**
   * 输出json格式到客户端
   *
   * @param data 待转换成json的对象
   */
  public static void outJson(HttpServletResponse response, Object data) {
    out(response, MediaType.APPLICATION_JSON_UTF8_VALUE, JSON.toJSONString(data));
  }

  /**
   * 输出 HTTP API Json 固定格式到客户端
   */
  public static void outJson(HttpServletResponse response, int code, String msg, Object data,
      Integer httpStatus) {
    StatefulBody bean = new StatefulBody();
    bean.setCode(code);
    bean.setMessage(msg);
    bean.setPayload(data);
    bean.setStatus(httpStatus);
//        bean.setUserData(userData);
    outJson(response, bean);
  }

  /**
   * 输出指定格式到客户端
   *
   * @param contentType 指定格式，可以调用本类中的常量
   * @param text 指定格式的内容
   */
  public static void out(HttpServletResponse response, String contentType, String text) {
    response.setContentType(contentType);
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    try {
      response.getWriter().write(text);
      response.getWriter().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
