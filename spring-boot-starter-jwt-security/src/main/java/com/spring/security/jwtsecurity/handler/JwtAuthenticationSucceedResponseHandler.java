package com.spring.security.jwtsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.jwtsecurity.auth_semi.JwtAuthenticationTokenFactory;
import com.spring.security.jwtsecurity.handler.parent.SucceedResponseHandler;
import com.spring.security.jwtsecurity.json.AuthSucceedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Prepare JwtAuthenticationSucceeded response body
 *
 * @author Houston(Nayana)
 **/
public class JwtAuthenticationSucceedResponseHandler implements SucceedResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationSucceedResponseHandler.class);

    @Override
    public void handle(HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws IOException {

        LOGGER.info("Start preparing auth body");
        String jsonBody;

        try {

            JwtAuthenticationTokenFactory.JWTAuthenticationToken authenticationToken = (JwtAuthenticationTokenFactory.JWTAuthenticationToken) authentication;

            jsonBody = (new ObjectMapper()).writeValueAsString(new AuthSucceedResponse.AuthSucceedResponseBuilder(
                    authenticationToken.getJwtToken(),
                    authenticationToken.getPrincipal().toString(),
                    authenticationToken.getAuthority(),
                    authenticationToken.getPermissions()).buid());

            LOGGER.info("JWT Authentication response body has been prepared successfully");
        } catch (Exception e) {
            LOGGER.error("Error preparing response body");
            jsonBody = e.getMessage();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonBody);
        LOGGER.info("JWT Token has been composed");
    }
}
