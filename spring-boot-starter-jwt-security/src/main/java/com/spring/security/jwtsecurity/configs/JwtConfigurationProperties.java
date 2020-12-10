package com.spring.security.jwtsecurity.configs;

/**
 * @author Houston(Nayana)
 **/

public class JwtConfigurationProperties {

    private JwtTokenConfigProperties token = new JwtTokenConfigProperties();
    private JwtAuthenticationConfigProperties auth = new JwtAuthenticationConfigProperties();

    public JwtTokenConfigProperties getToken() {
        return token;
    }

    public void setToken(JwtTokenConfigProperties token) {
        this.token = token;
    }

    public JwtAuthenticationConfigProperties getAuth() {
        return auth;
    }

    public void setAuth(JwtAuthenticationConfigProperties auth) {
        this.auth = auth;
    }
}
