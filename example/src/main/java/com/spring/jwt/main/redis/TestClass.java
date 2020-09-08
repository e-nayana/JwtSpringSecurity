package com.spring.jwt.main.redis;

import com.spring.jwt.main.redis.provider.RedisBaseValue;

/**
 * @author Houston(Nayana)
 **/

public class TestClass extends RedisBaseValue {

    Integer integer;


    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "integer=" + integer +
                '}';
    }
}
