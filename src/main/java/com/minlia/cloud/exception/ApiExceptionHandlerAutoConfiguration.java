package com.minlia.cloud.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.cloud.exception.handler.ApiExceptionHandler;
import com.minlia.cloud.exception.handler.RestErrorEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

/**
 * @author will
 */
@Configuration
@ConditionalOnProperty(prefix = "system.api-exception.handler", name = "enabled", havingValue = "true")
public class ApiExceptionHandlerAutoConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModule(new ProblemModule().withStackTraces(false));
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer problemObjectMapperModules() {
    return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.modules(
        new ProblemModule(),
        new ConstraintViolationProblemModule()
    );
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiExceptionHandler apiExceptionHandler() {
    return new ApiExceptionHandler();
  }

  @Bean
  @ConditionalOnMissingBean
  public RestErrorEndpoint restErrorEndpoint() {
    return new RestErrorEndpoint();
  }



  @Bean
  ProblemModule problemModule() {
    ProblemModule problemModule=new ProblemModule( );
    return problemModule;
  }

  /*
   * Module for serialization/deserialization of ConstraintViolationProblem.
   */
  @Bean
  ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }
}
