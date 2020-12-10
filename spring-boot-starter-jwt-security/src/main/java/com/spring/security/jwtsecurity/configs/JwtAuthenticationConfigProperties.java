package com.spring.security.jwtsecurity.configs;

import com.spring.security.jwtsecurity.CONSTANT;

/**
 * If configuration cannot be found, default values will be served
 * @author Houston(Nayana)
 **/
public class JwtAuthenticationConfigProperties {

    private String url = CONSTANT.AUTHENTICATING_URL;
    private String method = CONSTANT.AUTHENTICATING_METHOD;
    private String alpha = CONSTANT.KEY_ALPHA;
    private String beta = CONSTANT.KEY_BETA;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }
}
