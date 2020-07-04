package com.spring.jwt.jwtsecurity.handler.parent;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

public interface FailureResponseHandler {
    void handle(HttpServletResponse response, HttpServletRequest request, AuthenticationException exception) throws IOException;
}
