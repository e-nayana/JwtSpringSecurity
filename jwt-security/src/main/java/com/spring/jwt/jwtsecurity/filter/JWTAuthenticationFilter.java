package com.spring.jwt.jwtsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.jwtsecurity.CONSTANT;
import com.spring.jwt.jwtsecurity.authentication_token.JwtAuthenticationTokenFactory;
import com.spring.jwt.jwtsecurity.handler.JwtAuthenticationFailureHandler;
import com.spring.jwt.jwtsecurity.handler.JwtAuthenticationSucceedHandler;
import com.spring.jwt.jwtsecurity.json.AuthenticationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Houston(Nayana)
 **/

/**
 * This filter gets in to acting when CONSTANT.AUTHENTICATING_URL and CONSTANT.AUTHENTICATING_METHOD get hit
 * CONSTANT.AUTHENTICATING_URL, CONSTANT.AUTHENTICATING_METHOD, CONSTANT.KEY_ALPHA, CONSTANT.KEY_BETA can be changed according to the usage
 * CONSTANT.KEY_ALPHA for username
 * CONSTANT.KEY_BETA for password
 *
 * Authentication success and failure handlers have been passed to the super class
 * Super class handles the success and failure process in case authentication
 */
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher(CONSTANT.AUTHENTICATING_URL, CONSTANT.AUTHENTICATING_METHOD));
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationSuccessHandler(new JwtAuthenticationSucceedHandler());
        super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    }

    /**
     * Extract the keyAlpha and keyBeta from json request body
     * Validate keys
     * Generate the AuthenticationToke object using keys
     * Pass them to Authentication provider for further validations
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

        AuthenticationRequest authenticationJsonBody = readJsonRequest(httpServletRequest);

        LOGGER.info("Authenticating against " + authenticationJsonBody.getKeyAlpha());
        LOGGER.info("Authenticating against [PROTECTED] ");

        JwtAuthenticationTokenFactory.JWTAuthenticationToken authRequest =
                JwtAuthenticationTokenFactory.getByUsernamePassword(authenticationJsonBody.getKeyAlpha(),
                        authenticationJsonBody.getKeyBeta());
        this.setDetails(httpServletRequest, authRequest);

        LOGGER.info("Authenticating initiated by Authentication provider");
        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationTokenFactory.JWTAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Read json attributes in request
     * Once reads the request, all the properties will be erased
     * @param request
     * @return
     */
    private AuthenticationRequest readJsonRequest(HttpServletRequest request){
        LOGGER.info("Reading request is started");
        BufferedReader reader;
        AuthenticationRequest result = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            reader = request.getReader();
            result = mapper.readValue(reader, AuthenticationRequest.class);
            LOGGER.info("Reading request is finished successfully");
        }catch (Exception e){
            LOGGER.error("Error reading request " + e.getMessage());

        }
        Assert.notNull(result, "Request read error Result null");
        return result;
    }

}