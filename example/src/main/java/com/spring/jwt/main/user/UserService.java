package com.spring.jwt.main.user;

/**
 * @author Houston(Nayana)
 **/

public interface UserService {
    User findByEmail(String email);
}
