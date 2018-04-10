package com.minlia.cloud.exception;

import com.minlia.cloud.exception.handler.ApiExceptionHandler;
import com.minlia.cloud.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author will
 */
@Configuration
@ConditionalOnProperty(prefix = "system.api-exception.handler", name = "enabled", havingValue = "true")
public class ApiExceptionHandlerAutoConfiguration {


  @Bean
  @ConditionalOnMissingBean
  public ApiExceptionHandler apiExceptionHandler() {
    return new ApiExceptionHandler();
  }

  @Bean
  @ConditionalOnMissingBean
  public GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }


}
