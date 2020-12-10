package com.spring.jwt.main.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Houston(Nayana)
 **/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String userName);
}
