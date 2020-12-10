package com.spring.security.jwtsecurity.auth_semi;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Generate UsernamePasswordAuthenticationToken
 *
 * @author Houston(Nayana)
 **/
public interface AuthenticationTokenFactory {
    UsernamePasswordAuthenticationToken getByUsernamePassword(Object principal, Object credentials);
    UsernamePasswordAuthenticationToken getByJwtToken(String jwtToken);
}
