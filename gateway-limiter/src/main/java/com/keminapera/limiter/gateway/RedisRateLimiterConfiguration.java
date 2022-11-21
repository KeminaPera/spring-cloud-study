package com.keminapera.limiter.gateway;

import com.keminapera.limiter.RedisRateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @author KeminaPera
 */
@Configuration
@ConditionalOnProperty(name = "limit.type", havingValue = "gateway")
public class RedisRateLimiterConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    public RedisScript redisRequestRateLimiterScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("limit_scripts/request_rate_limiter.lua")));
        redisScript.setResultType(List.class);
        return redisScript;
    }

    @Bean
    public RedisRateLimiter redisRateLimiter(RedisTemplate redisTemplate,
                                             @Qualifier("redisRequestRateLimiterScript") RedisScript<List<Long>> redisScript) {
        return new GatewayRedisRateLimiter(redisTemplate, redisScript);
    }
}
