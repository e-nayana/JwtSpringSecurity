package com.spring.jwt.jwtsecurity.filter;

import com.spring.jwt.jwtsecurity.CONSTANT;
import com.spring.jwt.jwtsecurity.authentication_token.JwtAuthenticationTokenFactory;
import com.spring.jwt.jwtsecurity.handler.JwtAuthenticationFailureHandler;
import com.spring.jwt.jwtsecurity.handler.JwtRequestValidationSucceedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

/**
 * This filter get hit all the time when enpoind is called.Even when authentication is called
 * This filter validates, decodes the token in header
 *
 * Here AuthenticationSuccessHandler has been set up here and successfulAuthentication method has been override accordingly
 */
public class JwtTokenFissionFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationSuccessHandler successHandler = new JwtRequestValidationSucceedHandler();
    private AuthenticationFailureHandler failureHandler = new JwtAuthenticationFailureHandler();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    public JwtTokenFissionFilter(AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher(CONSTANT.MATCH_ALL_URL));
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationFailureHandler(failureHandler);
    }

    /**
     * Extract the token from the request
     * Validate token
     * Decode token
     * Pass claims to auth provider
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        LOGGER.info("Authentication initiated");
        LOGGER.info("Authenticating against " + httpServletRequest.getHeader(CONSTANT.HEADER_JWT_TOKEN_KEY));

        String jwtToken = httpServletRequest.getHeader(CONSTANT.HEADER_JWT_TOKEN_KEY);

        JwtAuthenticationTokenFactory.JWTAuthenticationToken authRequest = JwtAuthenticationTokenFactory.getByJwtToken(jwtToken);
        this.setDetails(httpServletRequest, authRequest);

        LOGGER.info("Authenticating initiated");
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationTokenFactory.JWTAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        rememberMeServices.loginSuccess(request, response, authResult);

        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }

        successHandler.onAuthenticationSuccess(request, response,chain, authResult);
    }

}
