package com.keminapera.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author KeminaPera
 */
@Slf4j
public class AFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("AFilter前置逻辑");
        System.out.println("AFilter前置逻辑");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("AFilter后置逻辑");
            System.out.println("AFilter后置逻辑");
        }));
    }
}
