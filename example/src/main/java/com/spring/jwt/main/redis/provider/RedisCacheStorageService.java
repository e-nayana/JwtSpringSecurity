package com.spring.jwt.main.redis.provider;

import com.spring.jwt.main.redis.TestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Houston(Nayana)
 **/

public class RedisCacheStorageService<K, V> extends RedisTemplate<K, V> implements CacheStorageService<K, V>{


    ValueOperations<K, V> valueOperations;

    public RedisCacheStorageService(LettuceConnectionFactory lettuceConnectionFactory, Class<V> valueType) {
        super();
        super.setKeySerializer(new StringRedisSerializer());
        super.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        super.setHashValueSerializer(new JdkSerializationRedisSerializer());
        super.setValueSerializer(new Jackson2JsonRedisSerializer<>(valueType));
        super.setConnectionFactory(lettuceConnectionFactory);

        this.valueOperations = this.opsForValue();
    }

    @Override
    public void put(K key, V value) {
        valueOperations.set(key, value);
    }

    @Override
    public V get(K key) {
        return valueOperations.get(key);
    }

    @Override
    public void update(K key, V value) throws Exception {
        if(exist(key)){
            valueOperations.set(key, value);
            return;
        }
        throw new Exception("");
    }

    @Override
    public Boolean exist(K key) {
        return valueOperations.get(key).getClass().isInstance(RedisBaseValue.class);
    }

    @Override
    public Boolean remove(Object key) {
        return null;
    }
}
