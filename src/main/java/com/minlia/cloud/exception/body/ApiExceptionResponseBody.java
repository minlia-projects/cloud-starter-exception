package com.minlia.cloud.exception.body;

import com.minlia.cloud.stateful.body.impl.FailureResponseBody;
import lombok.Data;

/**
 * @author will
 */
@Data
public class ApiExceptionResponseBody extends FailureResponseBody {


  private String developerMessage;


  public ApiExceptionResponseBody() {
  }

  public ApiExceptionResponseBody(final Integer code, final String message,
      final String developerMessage) {
    this.code = code;
    this.message = message;
    this.developerMessage = developerMessage;
  }

  public ApiExceptionResponseBody(final Integer code, final Integer status, final String message,
      final String developerMessage) {
    this(code, message, developerMessage);
    this.status = status;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(final String developerMessage) {
    this.developerMessage = developerMessage;
  }


}
