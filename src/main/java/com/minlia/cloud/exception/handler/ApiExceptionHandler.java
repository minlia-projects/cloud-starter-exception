package com.minlia.cloud.exception.handler;

import static org.zalando.problem.spring.common.MediaTypes.PROBLEM;

import java.util.Optional;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@Validated
@ControllerAdvice
public class ApiExceptionHandler implements ProblemHandling {
  @Override
  public boolean isCausalChainsEnabled() {
    return false;
  }

  public static final String PROBLEM_UTF8_VALUE = "application/problem+json;charset=UTF-8";
  public static final MediaType PROBLEM_UTF8 = MediaType.parseMediaType(PROBLEM_UTF8_VALUE);

  @Override
  public Optional<MediaType> negotiate(final NativeWebRequest request) {

    final Optional<MediaType> mediaType = ProblemHandling.super.negotiate(request);
    return mediaType
        .filter(PROBLEM::equals)
        // Using Spring Boot prior 2.0.0, use the following
        .map(mt -> Optional.of(PROBLEM_UTF8))
        // Using Spring Boot 2.0.0 and above, use the following
        // .map(mt -> Optional.of(MediaType.APPLICATION_PROBLEM_JSON_UTF8))
        .orElse(mediaType);
  }



  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonBuilder() {

    return builder ->
        // We must register problem-spring-web modules. See:
        // https://github.com/zalando/problem-spring-web#configuration
        builder.modulesToInstall(new ProblemModule(), new ConstraintViolationProblemModule());
  }


}