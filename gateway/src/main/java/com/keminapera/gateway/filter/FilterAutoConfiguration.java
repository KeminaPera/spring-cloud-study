package com.keminapera.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 用于测试filter执行顺序
 *
 * @author KeminaPera
 */
@Configuration
public class FilterAutoConfiguration {

    @Bean
    @Order(1)
    public GlobalFilter aFilter() {
        return new AFilter();
    }

    @Bean
    @Order(2)
    public GlobalFilter bFilter() {
        return new BFilter();
    }

    @Bean
    @Order(3)
    public GlobalFilter cFilter() {
        return new CFilter();
    }
}
