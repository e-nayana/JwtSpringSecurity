package com.spring.security.jwtsecurity.cache;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Houston(Nayana)
 **/

public abstract class HyperUserService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        /**
         * TODO fetch cache user
         * TODO fetch db user
         */


        return null;
    }


    /**
     * fetch user from cache
     */
    public abstract UserDetails hyperUser();

    /**
     * to be implemented to fetch user from db
     */
    public abstract UserDetails userDetails();





}
