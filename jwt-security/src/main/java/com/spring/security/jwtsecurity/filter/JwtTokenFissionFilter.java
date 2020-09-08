package com.spring.security.jwtsecurity.filter;

import com.spring.security.jwtsecurity.CONSTANT;
import com.spring.security.jwtsecurity.authentication_token.JwtAuthenticationTokenFactory;
import com.spring.security.jwtsecurity.cache.CacheSessionHandleManager;
import com.spring.security.jwtsecurity.cache.CacheSessionHandleService;
import com.spring.security.jwtsecurity.cache.implementations.CacheSessionHandleServiceImpl;
import com.spring.security.jwtsecurity.handler.JwtAuthenticationFailureHandler;
import com.spring.security.jwtsecurity.handler.JwtRequestValidationSucceedHandler;
import io.jsonwebtoken.lang.Assert;
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
 * This filter get in action when a endpoint is invoked.Even when authentication is called
 * This filter validates, decodes the token in header
 * If session handling providers are setup, session will be handle through them without using main database.
 * AuthenticationSuccessHandler has been set up here and successfulAuthentication method has been override accordingly
 */
public class JwtTokenFissionFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationSuccessHandler successHandler = new JwtRequestValidationSucceedHandler();
    private AuthenticationFailureHandler failureHandler = new JwtAuthenticationFailureHandler();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private CacheSessionHandleManager cacheSessionHandlerManager = new CacheSessionHandleServiceImpl();

    /**
     * No session providers
     * @param authenticationManager
     */
    public JwtTokenFissionFilter(AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher(CONSTANT.MATCH_ALL_URL));
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(failureHandler);
    }

    /**
     * Multi session providers
     * @param authenticationManager
     * @param cacheSessionHandleProviders
     */
    public JwtTokenFissionFilter(AuthenticationManager authenticationManager, CacheSessionHandleService cacheSessionHandleProviders){
        super(new AntPathRequestMatcher(CONSTANT.MATCH_ALL_URL));
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(failureHandler);
        cacheSessionHandlerManager = cacheSessionHandleProviders;
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

    /**
     * Handle session by cache session providers.
     * Here we haven't used separate delegator class to delegate the session handling action
     * @param authentication
     */
    private void cacheSessionHandle(Authentication authentication)
    {
        Assert.notNull(cacheSessionHandlerManager, "");
        cacheSessionHandlerManager.handle(authentication);
    }

    public CacheSessionHandleManager getCacheSessionHandlerManager() {
        return cacheSessionHandlerManager;
    }

    /**
     * Can be overrider for setting up proper cache session handler
     * @param cacheSessionHandlerManager
     */
    public void setCacheSessionHandlerManager(CacheSessionHandleManager cacheSessionHandlerManager) {
        this.cacheSessionHandlerManager = cacheSessionHandlerManager;
    }
}
