package com.spring.security.jwtsecurity.filter;

import com.spring.security.jwtsecurity.auth_semi.AuthenticationTokenFactory;
import com.spring.security.jwtsecurity.cache.CacheSessionHandleManager;
import com.spring.security.jwtsecurity.cache.CacheSessionHandleService;
import com.spring.security.jwtsecurity.cache.implementations.CacheSessionHandleServiceImpl;
import com.spring.security.jwtsecurity.extractor.BearerTokenExtractor;
import com.spring.security.jwtsecurity.extractor.HeaderPropertyExtractor;
import com.spring.security.jwtsecurity.handler.JwtAuthenticationFailureHandler;
import com.spring.security.jwtsecurity.handler.JwtRequestValidationSucceedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.*;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

public class JwtTokenSubstantiateFilter extends OncePerRequestFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationSuccessHandler successHandler = new JwtRequestValidationSucceedHandler();
    private AuthenticationFailureHandler failureHandler = new JwtAuthenticationFailureHandler();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private CacheSessionHandleManager cacheSessionHandlerManager = new CacheSessionHandleServiceImpl();
    private HeaderPropertyExtractor headerPropertyExtractor = new BearerTokenExtractor();
    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private AuthenticationTokenFactory jwtAuthenticationTokenFactory;
    private AuthenticationManager authenticationManager;

    /**
     * No session providers
     *
     * @param authenticationManager
     */
    public JwtTokenSubstantiateFilter(AuthenticationManager authenticationManager, AuthenticationTokenFactory jwtAuthenticationTokenFactory) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationTokenFactory = jwtAuthenticationTokenFactory;
    }

    /**
     * With Multi session providers
     *
     * @param authenticationManager
     * @param cacheSessionHandleProviders
     */
    public JwtTokenSubstantiateFilter(AuthenticationManager authenticationManager, AuthenticationTokenFactory jwtAuthenticationTokenFactory, CacheSessionHandleService cacheSessionHandleProviders) {

        this.authenticationManager = authenticationManager;
        this.cacheSessionHandlerManager = cacheSessionHandleProviders;
        this.jwtAuthenticationTokenFactory = jwtAuthenticationTokenFactory;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Request is to process authentication");
        }

        Authentication authResult;
        try {
            authResult = this.substantiate(request, response);
            if (authResult == null) {
                return;
            }

        } catch (InternalAuthenticationServiceException var8) {
            this.logger.error("An internal error occurred while trying to authenticate the user.", var8);
            this.unsuccessfulAuthentication(request, response, var8);
            return;
        } catch (AuthenticationException var9) {
            this.unsuccessfulAuthentication(request, response, var9);
            return;
        }

        this.successfulAuthentication(request, response, chain, authResult);
    }

    protected Authentication substantiate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        LOGGER.info("Authentication initiated by token");
        LOGGER.info("Authenticating against token");
        String jwtToken = (String) headerPropertyExtractor.extract(httpServletRequest);

        UsernamePasswordAuthenticationToken authRequest = jwtAuthenticationTokenFactory.getByJwtToken(jwtToken);
        this.setDetails(httpServletRequest, authRequest);

        LOGGER.info("Authenticating initiated");

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        rememberMeServices.loginSuccess(request, response, authResult);
        successHandler.onAuthenticationSuccess(request, response, chain, authResult);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication request failed: " + failed.toString(), failed);
            this.logger.debug("Updated SecurityContextHolder to contain null Authentication");
            this.logger.debug("Delegating to authentication failure handler " + this.failureHandler);
        }
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }


    /**
     * As JWT is stateless
     * Set auth details each time authentication happens.
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * JWT auth aware
     *
     * @return
     */
    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    /**
     * JWT auth aware
     *
     * @return
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * JWT auth aware
     *
     * @return
     */
    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }


    /**
     * Handle session by cache session providers.
     * Here we haven't used separate delegator class to delegate the session handling action
     *
     * @param authentication
     */
    private void cacheSessionHandle(Authentication authentication) {
        io.jsonwebtoken.lang.Assert.notNull(cacheSessionHandlerManager, "cache session manager can not be null");
        cacheSessionHandlerManager.handle(authentication);
    }

    protected CacheSessionHandleManager getCacheSessionHandlerManager() {
        return cacheSessionHandlerManager;
    }

    /**
     * Can be overrider for setting up proper cache session handler
     *
     * @param cacheSessionHandlerManager
     */
    public void setCacheSessionHandlerManager(CacheSessionHandleManager cacheSessionHandlerManager) {
        this.cacheSessionHandlerManager = cacheSessionHandlerManager;
    }
}
