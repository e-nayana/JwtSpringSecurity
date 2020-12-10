package com.spring.jwt.main.redis;

import com.spring.security.jwtsecurity.cache.CacheSessionHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;


/**
 * @author Houston(Nayana)
 **/

public class RedisCacheSessionProvider implements CacheSessionHandleService {

    Logger LOGGER = LoggerFactory.getLogger(RedisCacheSessionProvider.class);


    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @Override
    public void handle(Authentication authentication) {

        LOGGER.info("Starting cache checking in redis");


//        redisTemplate.opsForValue().set("bijjo", testClass);

        /**
         * Check in redis cache
         */
    }

    @Override
    public boolean support(Class<?> authentication) {

//        if(AuthenticationTokenFactory.JWTAuthenticationToken.class.isInstance(authentication)){
//            return true;
//        }
        return false;
    }
}
