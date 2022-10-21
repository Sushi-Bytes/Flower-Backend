package com.sushi.flower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.neo4j.config.EnableReactiveNeo4jAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
@EnableReactiveNeo4jAuditing
@EnableTransactionManagement
public class FlowerConfig {
    @Bean
    public ReactiveAuditorAware<String> reactiveAuditorAware() {
        return () -> Mono.just("hantsy");
    }
}
