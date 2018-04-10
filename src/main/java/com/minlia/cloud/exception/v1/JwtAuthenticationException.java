package com.minlia.cloud.exception.v1;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
