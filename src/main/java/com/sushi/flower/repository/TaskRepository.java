package com.sushi.flower.repository;

import com.sushi.flower.model.TaskEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveNeo4jRepository<TaskEntity, String> {
    Mono<TaskEntity> findOneByName(String name);
}
