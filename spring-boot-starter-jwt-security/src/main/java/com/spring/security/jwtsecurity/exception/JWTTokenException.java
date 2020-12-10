package com.spring.security.jwtsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Houston(Nayana)
 **/

public class JWTTokenException extends AuthenticationException {

    public JWTTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JWTTokenException(String msg) {
        super(msg);
    }
}
