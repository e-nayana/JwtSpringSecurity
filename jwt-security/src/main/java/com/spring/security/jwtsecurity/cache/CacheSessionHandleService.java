package com.spring.security.jwtsecurity.cache;

/**
 * @author Houston(Nayana)
 **/

public interface CacheSessionHandleService extends CacheSessionHandleManager {
    boolean  support(Class<?> authentication);
}
