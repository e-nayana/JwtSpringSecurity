package com.spring.jwt.main.redis;

import com.spring.jwt.main.redis.provider.RedisCacheStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Houston(Nayana)
 **/

@Service
public class RedisUserCache extends RedisCacheStorageService<String, UserDetails> implements UserCache {

    @Autowired
    public RedisUserCache(LettuceConnectionFactory lettuceConnectionFactory) {
        super(lettuceConnectionFactory, UserDetails.class);
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        return get(username);
    }

    @Override
    public void putUserInCache(UserDetails user) {
        put(user.getUsername(), user);
    }

    @Override
    public void removeUserFromCache(String username) {

    }
}
