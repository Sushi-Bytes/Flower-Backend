package com.sushi.flower.controller;

import com.sushi.flower.model.TaskEntity;
import com.sushi.flower.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    @PutMapping
    Mono<TaskEntity> createOrUpdateTask(@RequestBody TaskEntity newTask) {
        return taskRepository.save(newTask);
    }

    @GetMapping(value = { "", "/" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/by-task")
    Mono<TaskEntity> byTask(@RequestParam String name) {
        return taskRepository.findOneByName(name);
    }

    @DeleteMapping("{id}")
    Mono<Void> delete(@PathVariable String id) {
        return taskRepository.deleteById(id);
    }
}
