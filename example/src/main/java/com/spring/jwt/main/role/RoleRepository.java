package com.spring.jwt.main.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Houston(Nayana)
 **/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
