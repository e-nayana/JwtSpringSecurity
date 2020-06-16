package com.spring.jwt.jwtsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.jwtsecurity.authentication_token.JwtAuthenticationTokenFactory;
import com.spring.jwt.jwtsecurity.handler.parent.SucceedResponseHandler;
import com.spring.jwt.jwtsecurity.json.AuthSucceedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author houston-hash
 **/

public class JwtAuthenticationSucceedResponseHandler implements SucceedResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationSucceedResponseHandler.class);

    @Override
    public void handle(HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws IOException {

        LOGGER.info("Start preparing auth body");
        String jsonBody;

        try {
            jsonBody = (new ObjectMapper()).writeValueAsString(new AuthSucceedResponse(
                    ((JwtAuthenticationTokenFactory.JWTAuthenticationToken)authentication).getJwtToken()
                    ));
            LOGGER.info("JWT token body has been prepared successfully");
        }catch (Exception e){
            LOGGER.error("Error preparing response body");
            jsonBody = e.getMessage();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonBody);
        LOGGER.info("JWT Token has been composed");
    }
}
