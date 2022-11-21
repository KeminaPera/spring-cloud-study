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
public class CFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("CFilter前置处理");
        System.out.println("CFilter前置处理");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("CFilter后置处理");
            System.out.println("CFilter后置处理");
        }));
    }
}
