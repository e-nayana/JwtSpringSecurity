package com.spring.jwt.main;

import com.spring.jwt.main.redis.RedisUserCache;
import com.spring.security.jwtsecurity.WebSecurityConfigurerAdapterJWT;
import com.spring.security.jwtsecurity.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author Houston(Nayana)
 **/


public class SecurityConfigs {

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class WebSecurityConfigSpring extends WebSecurityConfigurerAdapterJWT {

        @Autowired
        @Resource(name = "userDetailServiceImpl")
        private UserDetailsService userDetailsService;

        @Autowired
        private RedisUserCache redisUserCache;

        @Override
        public UserCache userCache(){
            return redisUserCache;
        }


        @Override
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public UserDetailsService getUserDetailService(){
            return userDetailsService;
        }

        @Bean
        public TenantFilter tenantFilter(){
            return new TenantFilter();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            super.configure(http);
            http.addFilterBefore(tenantFilter(), JWTAuthenticationFilter.class);
        }
    }
}
