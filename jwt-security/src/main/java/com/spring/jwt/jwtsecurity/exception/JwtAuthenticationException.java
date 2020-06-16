package com.spring.jwt.jwtsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author houston-hash
 **/

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
