package com.spring.security.jwtsecurity.auth_semi;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Permission aware user details service {@link UserDetailsService}
 * This is the user details service interface must be use when using jwt authentication
 * @author Houston(Nayana)
 **/
public abstract class JwtUserDetailsService implements UserDetailsService {

    @Override
    public JwtUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userByUserName(email);
    }

    public abstract JwtUserDetails userByUserName(String email) throws UsernameNotFoundException;
}
