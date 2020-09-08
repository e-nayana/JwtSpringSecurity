package com.spring.security.jwtsecurity;

import com.spring.security.jwtsecurity.authentication_token.JwtAuthenticationTokenFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author Houston(Nayana)
 **/

public class JWTAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * true - hide the dao not found exception
     * false - show the dao not found exception instead of BadCredentials
     */
    boolean hideUserNotFoundExceptions = false;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    /**
     * userDetailService and passwordEncoder must be set in the super class as it calls these services with 'this' key word.
     * So super class always refers self properties.
     * @param userDetailsService
     * @param passwordEncoder
     */
    public JWTAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserCache userCache) {
        super();

        super.setUserDetailsService(userDetailsService);
        if(passwordEncoder != null)  super.setPasswordEncoder(passwordEncoder);
        if(userCache != null)  super.setUserCache(userCache);
    }

    @Override
    public boolean isHideUserNotFoundExceptions() {
        return hideUserNotFoundExceptions;
    }

    @Override
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {

        JwtAuthenticationTokenFactory.JWTAuthenticationToken result = new JwtAuthenticationTokenFactory.JWTAuthenticationToken(
                principal, authentication.getCredentials(),((JwtAuthenticationTokenFactory.JWTAuthenticationToken)authentication).getJwtToken(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));

        result.setDetails(authentication.getDetails());

        return result;
    }

}
