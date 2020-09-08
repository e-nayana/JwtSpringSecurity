package com.spring.jwt.main.redis.provider;

/**
 * @author Houston(Nayana)
 **/

public interface CacheStorageService<K, V>{

    V get(K key);

    void put(K key, V value);

    void update(K key, V value) throws Exception;

    Boolean exist(K key);

    Boolean remove(K key);
}
