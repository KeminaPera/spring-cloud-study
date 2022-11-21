package com.keminapera.limiter;

import com.keminapera.limiter.gateway.GatewayRedisRateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KeminaPera
 */
@RestController
public class RateLimiterController {

    @Resource
    private RedisRateLimiter rateLimiter;

    @GetMapping("/ping")
    public ResponseEntity<String> test() {
        if (rateLimiter.isAllowed("routeId", "ping")) {
            return ResponseEntity.ok("pong");
        } else {
            return ResponseEntity.ok("触发限流!!!");
        }
    }
}
