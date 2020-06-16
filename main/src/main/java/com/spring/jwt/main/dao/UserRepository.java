package com.spring.jwt.main.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houston-hash
 **/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Integer id);
    User findByEmail(String userName);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
