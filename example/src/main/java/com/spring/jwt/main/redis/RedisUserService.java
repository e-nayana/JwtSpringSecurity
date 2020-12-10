package com.spring.jwt.main.redis;

import com.spring.jwt.main.redis.provider.RedisCacheStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

/**
 * @author Houston(Nayana)
 **/

//@Service
public class RedisUserService extends RedisCacheStorageService<String, TestClass> {

//    @Autowired
    public RedisUserService(LettuceConnectionFactory lettuceConnectionFactory) {
        super(lettuceConnectionFactory, TestClass.class);
    }
}
