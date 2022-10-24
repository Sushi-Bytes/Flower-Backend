package com.sushi.flower.service;

import com.sushi.flower.model.Task;
import com.sushi.flower.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.neo4j.driver.Driver;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Flux<Task> searchTasksByName(String name) {
        return taskRepository.searchByName(name);
    }

    public Mono<Task> save(Task newTask) {
        return taskRepository.save(newTask);
    }

    public Flux<Task> findAll() {
        return taskRepository.findAll();
    }

    public Flux<Task> searchByName(String name) {
        return taskRepository.searchByName(name);
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }
}
