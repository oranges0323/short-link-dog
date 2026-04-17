package com.oranges.shortlinkdog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    /**
 * 配置RedisTemplate实例，用于操作Redis数据库
 * 设置Redis的连接工厂、序列化方式等关键配置
 *
 * @param redisConnectionFactory Redis连接工厂，用于创建Redis连接
 * @return 配置好的RedisTemplate实例，用于Redis操作
 */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置Redis连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 创建Jackson2JsonRedisSerializer实例，用于JSON序列化
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 设置key的序列化方式为String序列化
        template.setKeySerializer(RedisSerializer.string());
        // 设置hash key的序列化方式为String序列化
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式为JSON序列化
        template.setValueSerializer(jsonRedisSerializer);
        // 设置hash value的序列化方式为JSON序列化
        template.setHashValueSerializer(jsonRedisSerializer);
        // 返回配置好的RedisTemplate实例
        return template;
    }
}