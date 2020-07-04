package com.spring.jwt.jwtsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.jwtsecurity.handler.parent.FailureResponseHandler;
import com.spring.jwt.jwtsecurity.json.DefaultErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

/**
 * The strategy error body manipulate
 * This could be overwritten by implementing new handler by SucceedResponseHandler
 */
public class JwtFailureResponseHandler implements FailureResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFailureResponseHandler.class);

    @Override
    public void handle(HttpServletResponse response, HttpServletRequest request, AuthenticationException exception) throws IOException {

        LOGGER.info("Start preparing error body");
        String jsonBody;

        try {
            jsonBody = (new ObjectMapper()).writeValueAsString(new DefaultErrorResponse(
                    exception.getClass().toString(),
                    exception.getMessage(),
                    Integer.toString(HttpStatus.UNAUTHORIZED.value()),
                    exception.getLocalizedMessage(),
                    request.getRequestURI()));
            LOGGER.info("Error body has been prepared successfully");
        }catch (Exception e){
            LOGGER.error("Error preparing error body");
            jsonBody = e.getMessage();
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonBody);
        LOGGER.info("Error body has been composed");
    }
}
