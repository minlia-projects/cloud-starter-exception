package com.minlia.cloud.exception.util;

import com.minlia.cloud.context.EnvironmentHolder;
import java.net.URI;

public class ProblemType {

  public static URI withCode(Object code) {
    String problemEndpoint= EnvironmentHolder.getEnvironment().getProperty("system.developer.problem-portal-prefix");
    if(!problemEndpoint.endsWith("/")){
      problemEndpoint+="/";
    }
    return URI.create(problemEndpoint+code);
  }
}
