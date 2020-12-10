package com.spring.jwt.main.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Houston(Nayana)
 **/
@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
