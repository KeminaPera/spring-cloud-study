package com.keminapera.limiter;

import java.util.List;

/**
 * @author KeminaPera
 */
public interface RedisRateLimiter {

    List<String> getKeys(String id);

    boolean isAllowed(String routeId, String id);
}
