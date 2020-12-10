package com.spring.security.jwtsecurity.auth_semi;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Permission aware user details {@link UserDetails}
 * This is the user details interface must be use when using jwt
 *
 * @author Houston(Nayana)
 **/
public interface JwtUserDetails extends UserDetails {
    Collection<String> getPermissions();
}
