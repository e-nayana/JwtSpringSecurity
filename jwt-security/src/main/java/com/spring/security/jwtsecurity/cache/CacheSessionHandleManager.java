package com.spring.security.jwtsecurity.cache;

import org.springframework.security.core.Authentication;

/**
 * @author Houston(Nayana)
 **/

public interface CacheSessionHandleManager {


    void handle(Authentication authentication);

}
