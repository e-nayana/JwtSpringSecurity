package com.spring.jwt.jwtsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author houston-hash
 **/

public class JWTTokenException extends AuthenticationException {

    public JWTTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JWTTokenException(String msg) {
        super(msg);
    }
}