package com.spring.jwt.jwtsecurity.handler;

import com.spring.jwt.jwtsecurity.handler.parent.FailureResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

/**
 * SucceedResponseHandler is the strategy used here to send the error in response
 * handler could be configured as dao wish
 */
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFailureHandler.class);

    private FailureResponseHandler responseHandler = new JwtFailureResponseHandler();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info("Delegating to response handler");
        responseHandler.handle(response, request, exception);
    }

    public FailureResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(FailureResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }
}
