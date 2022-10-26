package com.sushi.flower.controller;

import com.sushi.flower.model.TaskLinkRequest;
import com.sushi.flower.model.Task;
import com.sushi.flower.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PutMapping
    Mono<Task> createOrUpdateTask(@RequestBody Task newTask) {
        return taskService.save(newTask);
    }

    @GetMapping(value = { "", "/" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Task> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/search-tasks")
    Flux<Task> byTask(@RequestParam String name) {
        return taskService.searchByName(name);
    }

    @DeleteMapping("{id}")
    Mono<Void> delete(@PathVariable Long id) {
        return taskService.deleteById(id);
    }

    @PutMapping("link")
    Flux<Task> linkTasks(@RequestBody TaskLinkRequest linkRequest) {
        return taskService.linkTasks(linkRequest);
    }
}
