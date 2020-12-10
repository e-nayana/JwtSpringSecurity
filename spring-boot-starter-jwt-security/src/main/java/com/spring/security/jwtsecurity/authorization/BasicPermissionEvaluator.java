package com.spring.security.jwtsecurity.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Houston(Nayana)
 **/

@Component
public class BasicPermissionEvaluator implements PermissionEvaluator {

    private final Logger LOGGER = LoggerFactory.getLogger(BasicPermissionEvaluator.class);


    @Override
    public boolean hasPermission(Authentication authentication, Object arg1, Object permission) {

        LOGGER.info("Permission evaluation starting on " , authentication);

//        List<UserPermit> userPermissions = this.userPermissionRepository.findByUserEmailAndPermissionName(userEmail,(String) permission);
//        UserPermit userPermit = null;
//        try {
//            userPermit = userPermissions.iterator().next();
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//        if (!permission.equals(userPermit.getPermissionName())) {
//            return false;
//        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication arg0, Serializable arg1, String arg2, Object arg3) {
        throw new RuntimeException("Id-based permission evaluation not currently supported.");
    }
}
