package com.spring.jwt.main.user.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.jwt.main.role.Role;
import com.spring.jwt.main.user.User;
import com.spring.security.jwtsecurity.auth_semi.JwtUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Houston(Nayana)
 **/
public class AuthUserEngine {

    public static class AuthUserFactory {

        public AuthUserFactory() {
        }

        public static JwtUserDetails create(User user) {
            Assert.notNull(user.getRoles(), "User Authorities cannot be null");
            Assert.notNull(user.getId(), "User Primary key cannot be null");
            Assert.notNull(user.getEmail(), "User Email cannot be null");
            Assert.notNull(user.getPassword(), "User Password cannot be null");
            Assert.notNull(user.getActive(), "User Status cannot be null");

            return new AuthUser(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getPassword(),
                    user.getGoogleAuthToken(),
                    user.getActive(),
                    mapToGrantedAuthorities(user.getRoles()),
                    mapGrantedPermissions(user.getRoles())
            );
        }


        private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }

        private static List<String> mapGrantedPermissions(List<Role> roles) {
            return roles.stream()
                    .map(role -> role.getPermissions())
                    .collect(Collectors.toList()).stream()
                    .flatMap(Collection::stream)
                    .map(permission -> permission.getName())
                    .collect(Collectors.toList());
        }

    }

    public static class AuthUser implements JwtUserDetails {


        private final Long id;
        private final String email;
        private final String username;
        private final String name;
        private final String password;
        private final String google_auth_token;
        private final boolean status;
        private final Collection<? extends GrantedAuthority> authorities;
        private final Collection<String> permissions;


        public AuthUser(Long id, String email, String name, String password, String google_auth_token, boolean status, Collection<? extends GrantedAuthority> authorities, Collection<String> permissions) {
            this.id = id;
            this.email = email;
            this.username = email;
            this.name = name;
            this.password = password;
            this.google_auth_token = google_auth_token;
            this.status = status;
            this.authorities = authorities;
            this.permissions = permissions;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getGoogle_auth_token() {
            return google_auth_token;
        }

        public boolean getStatus() {
            return status;
        }

        @Override
        public Collection<String> getPermissions() {
            return permissions;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public boolean isEnabled() {
            return status;
        }
    }
}
