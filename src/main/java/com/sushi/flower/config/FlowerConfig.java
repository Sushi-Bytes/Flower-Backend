package com.sushi.flower.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableReactiveNeo4jAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@EnableReactiveNeo4jAuditing
@EnableTransactionManagement
public class FlowerConfig {
}
