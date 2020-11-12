package com.spring.jwt.main.redis.provider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author Houston(Nayana)
 **/

@Component
public class RedisConfiguration
{
    @Bean
//    @Scope("singleton")
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()

                .master("mymaster")
                .sentinel("127.0.0.1", 5000)
                .sentinel("127.0.0.1", 5001)
                .sentinel("127.0.0.1", 5002);

        sentinelConfig.setPassword("123456");
        return new LettuceConnectionFactory(sentinelConfig);
    }

//    @Bean()
//    @ConditionalOnBean(name = "lettuceConnectionFactory")
//    public RedisTemplate<String, String> redisTemplatex(@Qualifier("lettuceConnectionFactory") RedisConnectionFactory lettuceConnectionFactory){
//
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
//        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisBaseValue.class));
//        template.setConnectionFactory(lettuceConnectionFactory);
//        return template;
//    }
}
