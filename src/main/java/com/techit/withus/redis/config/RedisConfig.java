package com.techit.withus.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig
{
    /**
     * Redis를 사용하기 위한 Configuration
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        // Redis는 Jedis와 Lettuce 두 가지로 나뉘는데, Lettuce를 사용
        return new LettuceConnectionFactory();
    }
}
