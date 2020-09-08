package com.spring.security.jwtsecurity;

import com.spring.security.jwtsecurity.matcher.JwtRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationEntryPoint.class);

    private final RequestMatcher DEFAULT_REQUEST_MATCHER = new JwtRequestMatcher();
    private static final String KEY_ALPHA = "username";
    private static final String KEY_BETA = "password";

    private RequestMatcher requestMatcher;

    public JWTAuthenticationEntryPoint() {
        this.requestMatcher = DEFAULT_REQUEST_MATCHER;
    }

    public JWTAuthenticationEntryPoint(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {


        /**
         * TODO      Prepare the response using response handler
         *
         */

        LOGGER.info("Start matching URI with " + ((JwtRequestMatcher)DEFAULT_REQUEST_MATCHER).getAutheticate_url());
        if (requestMatcher.matches(httpServletRequest)) {
            LOGGER.info("Authentication url is matched");


            LOGGER.info("Authenticating against " + httpServletRequest.getParameter(KEY_ALPHA));
            LOGGER.info("Authenticating against " + httpServletRequest.getParameter(KEY_BETA));


        } else {
            LOGGER.error("Unauthorized has been propagated further");
            commenceUnauthorizedResponse(httpServletRequest, httpServletResponse);
        }

    }

    public RequestMatcher getRequestMatcher() {
        return requestMatcher;
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    protected void commenceUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
