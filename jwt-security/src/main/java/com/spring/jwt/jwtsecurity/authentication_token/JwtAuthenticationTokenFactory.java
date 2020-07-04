package com.spring.jwt.jwtsecurity.authentication_token;

/**
 * @author Houston(Nayana)
 **/



import com.spring.jwt.jwtsecurity.CONSTANT;
import com.spring.jwt.jwtsecurity.exception.JWTTokenException;
import com.spring.jwt.jwtsecurity.jwt.JWTEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Factory design pattern to construct the JWTAuthenticationToken in difference situation inside the filters.
 * Case 1 -----> when /authenticate (username, password) validate username password
 * Case 2 -----> when validating token (token, username, password) validate token and set username password
 *
 *
 * Factory design pattern to construct the JWTAuthenticationToken in difference situation inside the authentication provier.
 *
 * Case 3------> when feeding security context (token, username, password)
 */
public class JwtAuthenticationTokenFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFactory.class);
    /**
     * Case 1
     * validate key_
     * @param principal
     * @param credentials
     * @return
     */
    public static JWTAuthenticationToken getByUsernamePassword(Object principal, Object credentials){

        Assert.notNull(principal, CONSTANT.KEY_ALPHA_VALIDATION_ERROR);
        Assert.notNull(credentials, CONSTANT.KEY_BETA_VALIDATION_ERROR);

        String jwtToken = JWTEngine.generate(CONSTANT.KEY_ALPHA, CONSTANT.KEY_BETA,principal, credentials);

        return new JWTAuthenticationToken(principal, credentials, jwtToken);
    }

    /**
     * Case 2
     * Validate the jwt token
     * set up @{KEY_BETA} and KEY_ALPHA
     * @param jwtToken
     * @return
     */
    public static JWTAuthenticationToken getByJwtToken(String jwtToken){

        LOGGER.info("Validating Token initiated");
        if(jwtToken == null || jwtToken.equals(CONSTANT.EMPTY_STRING) || !JWTEngine.validate(jwtToken)){
            LOGGER.error("Token is null, empty or invalid");
            throw new JWTTokenException(CONSTANT.JWT_TOKEN_VALIDATION_ERROR);
        }
        LOGGER.info("Token validated");

        LOGGER.info("Constructing JWTAuthenticationToken for authentication");
        JWTAuthenticationToken authToken = new JWTAuthenticationToken(JWTEngine.getAttribute(jwtToken, CONSTANT.KEY_ALPHA), JWTEngine.getAttribute(jwtToken, CONSTANT.KEY_BETA), jwtToken);

        LOGGER.info("Authenticating initiated");
        return authToken;
    }


    public static class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken {
        private static final long serialVersionUID = 490L;

        private final Object principal;
        private Object credentials;
        private String jwtToken;

        public JWTAuthenticationToken(Object principal, Object credentials, String jwtToken) {
            super(principal, credentials);
            this.principal = principal;
            this.credentials = credentials;
            this.jwtToken = jwtToken;
        }

        public JWTAuthenticationToken(Object principal, Object credentials, String jwtToken, Collection<? extends GrantedAuthority> authorities) {
            super(principal, credentials, authorities);
            this.principal = principal;
            this.credentials = credentials;
            this.jwtToken = jwtToken;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }

    }


}
