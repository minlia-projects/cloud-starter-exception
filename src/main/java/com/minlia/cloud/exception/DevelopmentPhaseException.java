package com.minlia.cloud.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

/**
 * 开发阶段异常，带修复指导 ID
 * @author will
 */
public class DevelopmentPhaseException extends NestedRuntimeException {

  private String tutorial;

  public DevelopmentPhaseException(String msg) {
    super(msg);
  }

  public DevelopmentPhaseException(@Nullable String msg,
      @Nullable Throwable cause) {
    super(msg, cause);
  }

  public String getTutorial() {
    return tutorial;
  }

  public void setTutorial(String tutorial) {
    this.tutorial = tutorial;
  }
}
