package com.spring.security.jwtsecurity;

import com.spring.security.jwtsecurity.auth_semi.JwtAuthenticationTokenFactory;
import com.spring.security.jwtsecurity.configs.JwtAuthenticationConfigProperties;
import com.spring.security.jwtsecurity.configs.JwtConfigurationProperties;
import com.spring.security.jwtsecurity.configs.JwtTokenConfigProperties;
import com.spring.security.jwtsecurity.filter.JWTAuthenticationFilter;
import com.spring.security.jwtsecurity.filter.JwtTokenFissionFilter;
import com.spring.security.jwtsecurity.jwt.JWTEngine;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * @author Houston(Nayana)
 **/

public abstract class WebSecurityConfigurerAdapterJWT extends WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {


    @Bean
    @ConfigurationProperties(prefix = "spring.houseton.security.jwt")
    public JwtConfigurationProperties configurationProperties(){
        return new JwtConfigurationProperties();
    }

    @Bean
    public JWTEngine jwtEngine(){
        return new JWTEngine(configurationProperties().getToken());
    }

    @Bean
    public JwtAuthenticationTokenFactory jwtAuthenticationTokenFactory(){
        return new JwtAuthenticationTokenFactory(jwtEngine());
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(super.authenticationManagerBean(), jwtAuthenticationTokenFactory(), configurationProperties().getAuth());
    }

    @Bean
    public JwtTokenFissionFilter jwtTokenFissionFilter() throws Exception{
        return new JwtTokenFissionFilter(super.authenticationManagerBean(), jwtAuthenticationTokenFactory());
    }

    public JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JWTAuthenticationEntryPoint();
    }

    public JWTAuthenticationProvider jwtAuthenticationProvider(){
        return new JWTAuthenticationProvider(getUserDetailService(), getPasswordEncoder(), userCache());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.authenticationProvider(jwtAuthenticationProvider());
        http.addFilterBefore(jwtTokenFissionFilter(), AnonymousAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), JwtTokenFissionFilter.class);
    }


    /**
     * If encoding passwords is not necessary in current case, return null
     * Password will be shown as it is.
     * @return
     */
    public abstract PasswordEncoder getPasswordEncoder();

    /**
     * {@link UserDetailsService} is required
     * @return
     */
    public abstract UserDetailsService getUserDetailService();

    /**
     * If caching users is not necessary in current case, return null.
     * If userCache returns null there will be not proper caching strategy.
     * @return
     */
    public abstract UserCache userCache();

}
