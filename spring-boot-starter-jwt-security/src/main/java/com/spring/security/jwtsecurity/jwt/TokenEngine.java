package com.spring.security.jwtsecurity.jwt;

/**
 * @author Houston(Nayana)
 **/

public interface TokenEngine {
    String generate(String principleKey, String credentialsKey, Object principal, Object credentials);
    Boolean validate(String jwtToken);
    Object getAttribute(String jwtToken, String attributeKey);
    void postConstruct();


}
