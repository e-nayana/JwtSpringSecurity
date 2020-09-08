package com.spring.security.jwtsecurity.matcher;

import com.spring.security.jwtsecurity.CONSTANT;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Houston(Nayana)
 **/

public class JwtRequestMatcher implements RequestMatcher {

    private String AUTHENTICATE_DEFAULT_URL = "/authenticate";

    private String autheticate_url;

    public JwtRequestMatcher() {
        this.autheticate_url = AUTHENTICATE_DEFAULT_URL;
    }

    public JwtRequestMatcher(String autheticate_url) {
        Assert.notNull(autheticate_url, CONSTANT.APPLYED_VALUE_ERROR);
        Assert.notNull(autheticate_url, CONSTANT.APPLYED_VALUE_ERROR);
        this.autheticate_url = autheticate_url;
    }

    @Override
    public boolean matches(HttpServletRequest request){
        return request.getRequestURI().equals(this.autheticate_url);
    }

    public String getAutheticate_url() {
        return autheticate_url;
    }
}
