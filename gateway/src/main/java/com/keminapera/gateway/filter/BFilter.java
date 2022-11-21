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
public class BFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("BFilter前置处理");
        System.out.println("BFilter前置处理");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("BFilter后置处理");
            System.out.println("BFilter后置处理");
        }));
    }
}
