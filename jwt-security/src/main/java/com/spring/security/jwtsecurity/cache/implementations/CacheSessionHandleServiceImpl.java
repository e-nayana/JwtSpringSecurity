package com.spring.security.jwtsecurity.cache.implementations;

import com.spring.security.jwtsecurity.cache.CacheSessionHandleService;
import org.springframework.security.core.Authentication;

/**
 * @author Houston(Nayana)
 **/

public class CacheSessionHandleServiceImpl implements CacheSessionHandleService {
    @Override
    public boolean support(Class<?> authentication) {
        return false;
    }

    @Override
    public void handle(Authentication authentication) {

    }
}
