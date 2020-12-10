package com.spring.jwt.main.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Houston(Nayana)
 **/

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> getByName(String name);
}
