package com.ss.redisdemo.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Autowired
	private Environment environment;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(20);
		poolConfig.setMaxIdle(5);
		poolConfig.setMinIdle(2);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);

		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
		factory.setHostName(environment.getProperty("redis.hostname"));
		factory.setPassword(environment.getProperty("redis.password"));
		factory.setPort(Integer.parseInt(environment.getProperty("redis.port")));
		factory.setUsePool(Boolean.parseBoolean(environment.getProperty("redis.usepool")));
		factory.setDatabase(Integer.parseInt(environment.getProperty("redis.databaseid")));
		return factory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}
	
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration cacheConfiguration = 
	            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)); // This sets the time to live for cache entries
		
		// Return a new RedisCacheManager built with default configurations and transaction awareness
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration)
            .transactionAware().build();
	}
	
	@Bean
  	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object param : params) {
					sb.append(param.toString());
				}
				return sb.toString();
			}
		};
	}
}
