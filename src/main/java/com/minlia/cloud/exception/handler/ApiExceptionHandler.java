package com.minlia.cloud.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ApiExceptionHandler implements ProblemHandling {

}