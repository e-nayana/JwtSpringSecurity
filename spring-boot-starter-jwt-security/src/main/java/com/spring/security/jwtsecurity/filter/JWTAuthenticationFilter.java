package com.spring.security.jwtsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.jwtsecurity.auth_semi.AuthenticationTokenFactory;
import com.spring.security.jwtsecurity.configs.JwtAuthenticationConfigProperties;
import com.spring.security.jwtsecurity.handler.JwtAuthenticationFailureHandler;
import com.spring.security.jwtsecurity.handler.JwtAuthenticationSucceedHandler;
import com.spring.security.jwtsecurity.json.AuthenticationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
 * <p>
 * Authentication success and failure handlers have been passed to the super class
 * Super class handles the success and failure process in case authentication
 */
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationTokenFactory jwtAuthenticationTokenFactory;
    private JwtAuthenticationConfigProperties configProperties;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationTokenFactory jwtAuthenticationTokenFactory,
                                   JwtAuthenticationConfigProperties configProperties) {
        super(new AntPathRequestMatcher(configProperties.getUrl(), configProperties.getMethod()));
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationSuccessHandler(new JwtAuthenticationSucceedHandler());
        super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        this.jwtAuthenticationTokenFactory = jwtAuthenticationTokenFactory;
        this.configProperties = configProperties;
    }

    /**
     * Extract the keyAlpha and keyBeta from json request body
     * Validate keys
     * Generate the AuthenticationToke object using keys
     * Pass them to Authentication provider for further validations
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        LOGGER.info("Authentication initiated.");

        AuthenticationRequest authenticationJsonBody = readJsonRequest(httpServletRequest);

        LOGGER.info("Authenticating against " + authenticationJsonBody.getKeyAlpha());
        LOGGER.info("Authenticating against [PROTECTED] ");

        UsernamePasswordAuthenticationToken authRequest =
                jwtAuthenticationTokenFactory.getByUsernamePassword(authenticationJsonBody.getKeyAlpha(),
                        authenticationJsonBody.getKeyBeta());
        this.setDetails(httpServletRequest, authRequest);

        LOGGER.info("Authenticating initiated by Authentication provider");

        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Read json attributes in request
     * Once reads the request, all the properties will be erased
     *
     * @param request
     * @return
     */
    private AuthenticationRequest readJsonRequest(HttpServletRequest request) {
        LOGGER.info("Reading request is started");
        BufferedReader reader;
        AuthenticationRequest result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            reader = request.getReader();
            result = mapper.readValue(reader, AuthenticationRequest.class);
            LOGGER.info("Reading request is finished successfully");
        } catch (Exception e) {
            LOGGER.error("Error reading request " + e.getMessage());

        }
        Assert.notNull(result, "Request read error Result null");
        return result;
    }

}