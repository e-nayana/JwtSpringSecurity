package com.spring.jwt.jwtsecurity.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.spring.jwt.jwtsecurity.CONSTANT;

/**
 * @author Houston(Nayana)
 **/

public class AuthSucceedResponse {
    @JsonAlias(CONSTANT.HEADER_JWT_TOKEN_KEY)
    private String token;

    public AuthSucceedResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
