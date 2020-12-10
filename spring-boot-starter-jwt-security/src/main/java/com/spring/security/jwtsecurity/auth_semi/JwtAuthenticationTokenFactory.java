package com.spring.security.jwtsecurity.auth_semi;

import com.spring.security.jwtsecurity.CONSTANT;
import com.spring.security.jwtsecurity.exception.JWTTokenException;
import com.spring.security.jwtsecurity.jwt.TokenEngine;
import com.spring.security.jwtsecurity.jwt.JWTEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Houston(Nayana)
 * * Factory design pattern to construct the JWTAuthenticationToken in difference situation inside the filters.
 * Case 1 -----> when /authenticate (username, password) validate username password
 * Case 2 -----> when validating token (token, username, password) validate token and set username password
 *
 * Factory design pattern to construct the JWTAuthenticationToken in difference situation inside the authentication provier.
 *
 * Case 3------> when feeding security context (token, username, password)
 **/
public class JwtAuthenticationTokenFactory implements AuthenticationTokenFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFactory.class);

    private TokenEngine jwtEngine;

    public JwtAuthenticationTokenFactory(TokenEngine jwtEngine) {
        this.jwtEngine = jwtEngine;
    }

    /**
     * Case 1
     * validate key_
     * @param principal
     * @param credentials
     * @return
     */
    @Override
    public UsernamePasswordAuthenticationToken getByUsernamePassword(Object principal, Object credentials){

        Assert.notNull(principal, CONSTANT.KEY_ALPHA_VALIDATION_ERROR);
        Assert.notNull(credentials, CONSTANT.KEY_BETA_VALIDATION_ERROR);

        String jwtToken = jwtEngine.generate(CONSTANT.KEY_ALPHA, CONSTANT.KEY_BETA,principal, credentials);

        return new JWTAuthenticationToken(principal, credentials, jwtToken);
    }

    /**
     * Case 2
     * Validate the jwt token
     * set up @{KEY_BETA} and KEY_ALPHA
     * @param jwtToken
     * @return
     */
    @Override
    public UsernamePasswordAuthenticationToken getByJwtToken(String jwtToken){

        LOGGER.info("Validating Token initiated");
        if(jwtToken == null || jwtToken.equals(CONSTANT.EMPTY_STRING) || !jwtEngine.validate(jwtToken)){
            LOGGER.error("Token is null, empty or invalid");
            throw new JWTTokenException(CONSTANT.JWT_TOKEN_VALIDATION_ERROR);
        }
        LOGGER.info("Token validated");

        LOGGER.info("Constructing JWTAuthenticationToken for authentication");
        JWTAuthenticationToken authToken = new JWTAuthenticationToken(jwtEngine.getAttribute(jwtToken, CONSTANT.KEY_ALPHA), jwtEngine.getAttribute(jwtToken, CONSTANT.KEY_BETA), jwtToken);

        LOGGER.info("Authenticating initiated");
        return authToken;
    }


    public static class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken {
        private static final long serialVersionUID = 490L;

        private final Object principal;
        private Object credentials;
        private Collection<? extends GrantedAuthority> authorities;
        private Collection<String> permissions;
        private String jwtToken;

        public JWTAuthenticationToken(Object principal, Object credentials, String jwtToken) {
            super(principal, credentials);
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

        public void setCredentials(Object credentials) {
            this.credentials = credentials;
        }

        public void setAuthority(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
        }

        public Collection<? extends GrantedAuthority> getAuthority() {
            return this.authorities;
        }

        public Collection<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(Collection<String> permissions) {
            this.permissions = permissions;
        }
    }
}
