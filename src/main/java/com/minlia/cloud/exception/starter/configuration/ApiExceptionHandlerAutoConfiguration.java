package com.minlia.cloud.exception.starter.configuration;

import com.minlia.cloud.exception.handler.ApiExceptionHandler;
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
  public ApiExceptionHandler apiExceptionAdvice() {
    return new ApiExceptionHandler();
  }

}
