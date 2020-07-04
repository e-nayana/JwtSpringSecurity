package com.spring.jwt.jwtsecurity;

import com.spring.jwt.jwtsecurity.filter.JWTAuthenticationFilter;
import com.spring.jwt.jwtsecurity.filter.JwtTokenFissionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * @author Houston(Nayana)
 **/

public abstract class WebSecurityConfigurerAdapterJWT extends WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(super.authenticationManagerBean());
    }

    @Bean
    public JwtTokenFissionFilter jwtTokenFissionFilter() throws Exception{
        return new JwtTokenFissionFilter(super.authenticationManagerBean());
    }

    public JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JWTAuthenticationEntryPoint();
    }

    public JWTAuthenticationProvider jwtAuthenticationProvider(){
        return new JWTAuthenticationProvider(getUserDetailService(), getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();


        http.authorizeRequests().anyRequest().has

        http.authenticationProvider(jwtAuthenticationProvider());
        http.addFilterBefore(jwtTokenFissionFilter(), AnonymousAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), JwtTokenFissionFilter.class);
    }


    public abstract PasswordEncoder getPasswordEncoder();

    public abstract UserDetailsService getUserDetailService();

}
