package com.spring.security.jwtsecurity.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.spring.security.jwtsecurity.CONSTANT;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Houston(Nayana)
 **/

public class AuthSucceedResponse {
    @JsonAlias(CONSTANT.HEADER_JWT_TOKEN_KEY)
    private String token;
    @JsonAlias(CONSTANT.AUTH_SUCCESS_KEY_ALPHA)
    private String userName;
    @JsonAlias(CONSTANT.AUTH_SUCCESS_KEY_ROLE)
    private Collection<? extends GrantedAuthority> authorities;
    @JsonAlias(CONSTANT.AUTH_SUCCESS_KEY_PERMISSION)
    private Collection<String> permissions;

    public AuthSucceedResponse(AuthSucceedResponseBuilder builder) {
        this.token = builder.token;
        this.userName = builder.userName;
        this.authorities = builder.authorities;
        this.permissions = builder.permissions;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }

    /**
     * Builder
     */
    public static class AuthSucceedResponseBuilder{

        private String token;
        private String userName;
        private Collection<? extends GrantedAuthority> authorities;
        private Collection<String> permissions;

        public AuthSucceedResponseBuilder(String token) {
            this.token = token;
        }

        public AuthSucceedResponseBuilder(String token, String userName, Collection<? extends GrantedAuthority> authorities, Collection<String> permissions) {
            this.token = token;
            this.userName = userName;
            this.authorities = authorities;
            this.permissions = permissions;
        }

        public AuthSucceedResponseBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public AuthSucceedResponseBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public AuthSucceedResponseBuilder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public AuthSucceedResponseBuilder setPermissions(Collection<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public AuthSucceedResponse buid(){
            return new AuthSucceedResponse(this);
        }

    }
}
