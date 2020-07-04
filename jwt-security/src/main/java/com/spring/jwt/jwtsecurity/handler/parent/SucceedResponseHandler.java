package com.spring.jwt.jwtsecurity.handler.parent;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

/**
 *
 */
public interface SucceedResponseHandler {

    void handle(HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws IOException;
}
