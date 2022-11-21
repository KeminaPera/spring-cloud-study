package com.keminapera.limiter.gateway;

import com.keminapera.limiter.RedisRateLimiter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring cloud gateway 限流原理
 *
 * @author KeminaPera
 */
@Slf4j
public class GatewayRedisRateLimiter implements RedisRateLimiter {

    private Map<String, Config> config = new HashMap<>();
    private RedisTemplate<String, String> redisTemplate;
    private RedisScript<List<Long>> script;
    private Config defaultConfig;

    public GatewayRedisRateLimiter(RedisTemplate<String, String> redisTemplate, RedisScript<List<Long>> script) {
        this.script = script;
        this.redisTemplate = redisTemplate;
        this.defaultConfig = Config.builder().replenishRate(1).requestedTokens(1).burstCapacity(5).build();
    }

    @Override
    public List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "request_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    @Override
    public boolean isAllowed(String routeId, String id) {
        Config routeConfig = this.config.getOrDefault(routeId, defaultConfig);
        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        // How many tokens are requested per request?
        int requestedTokens = routeConfig.getRequestedTokens();

        List<String> keys = getKeys(id);
        //List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "", 6 + "", requestedTokens + "");
        String[] scriptArgs = new String[]{replenishRate + "", burstCapacity + "", "", requestedTokens + ""};
        List<Long> result = redisTemplate.execute(this.script, keys, scriptArgs);
        log.info("keys={}, allowed_num={}， new_tokens={}", keys, result.get(0), result.get(1));
        for (String key : keys) {
            log.info("key={}， value={}", key, redisTemplate.boundValueOps(key).get());
        }
        // todo:
        return result.get(0) > 0;
    }

    @Validated
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        @Min(1)
        private int replenishRate;
        @Min(0)
        private int burstCapacity = 1;
        @Min(1)
        private int requestedTokens = 1;
    }
}
