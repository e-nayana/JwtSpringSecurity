package com.spring.jwt.main;

import com.spring.jwt.jwtsecurity.WebSecurityConfigurerAdapterJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author houston-hash
 **/


public class SecurityConfigs {

    @Configuration
    @EnableWebSecurity
    public static class WebSecurityConfigSpring extends WebSecurityConfigurerAdapterJWT {

        @Autowired
        @Resource(name = "userDetailServiceImpl")
        private UserDetailsService userDetailsService;


        @Override
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public UserDetailsService getUserDetailService(){
            return userDetailsService;
        }
    }




}
