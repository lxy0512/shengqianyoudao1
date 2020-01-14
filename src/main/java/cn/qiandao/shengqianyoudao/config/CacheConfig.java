package cn.qiandao.shengqianyoudao.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fyy
 * @date 2019/12/27 14:54
 **/
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheManager redisCacheManager =
                RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig(10000))
                .withInitialCacheConfigurations(initCacheConfigMap())
                .transactionAware()
                .build();
        return redisCacheManager;
    }

    private RedisCacheConfiguration defaultCacheConfig(Integer second){
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(second))
                        .serializeKeysWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(serializer))
                        .disableCachingNullValues();
        return config;
    }

    private Map<String, RedisCacheConfiguration> initCacheConfigMap() {
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("User", this.defaultCacheConfig(1000));
        configMap.put("User1", this.defaultCacheConfig(1000));
        configMap.put("User2", this.defaultCacheConfig(1000));
        configMap.put("User3", this.defaultCacheConfig(1000));
        return configMap;
    }
}
