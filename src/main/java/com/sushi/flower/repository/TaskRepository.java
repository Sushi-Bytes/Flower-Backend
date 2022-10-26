package com.sushi.flower.repository;

import com.sushi.flower.model.Task;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveNeo4jRepository<Task, Long> {
    @Query("MATCH (task:Task) WHERE task.name CONTAINS $name RETURN task")
    Flux<Task> searchByName(@Param("name") String name);
}
