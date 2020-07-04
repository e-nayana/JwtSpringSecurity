package com.spring.jwt.jwtsecurity.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.spring.jwt.jwtsecurity.CONSTANT;

/**
 * @author Houston(Nayana)
 **/

public class AuthenticationRequest {

    @JsonAlias(CONSTANT.KEY_ALPHA)
    private String keyAlpha;
    @JsonAlias(CONSTANT.KEY_BETA)
    private String keyBeta;

    public String getKeyAlpha() {
        return keyAlpha;
    }

    public void setKeyAlpha(String keyAlpha) {
        this.keyAlpha = keyAlpha;
    }

    public String getKeyBeta() {
        return keyBeta;
    }

    public void setKeyBeta(String keyBeta) {
        this.keyBeta = keyBeta;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "keyAlpha='" + keyAlpha + '\'' +
                ", keyBeta='" + keyBeta + '\'' +
                '}';
    }
}
