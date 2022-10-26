package com.sushi.flower.service;

import com.sushi.flower.model.TaskLinkRequest;
import com.sushi.flower.model.Task;
import com.sushi.flower.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Mono<Task> save(Task newTask) {
        return taskRepository.save(newTask);
    }

    public Flux<Task> findAll() {
        return taskRepository.findAll();
    }

    public Flux<Task> searchByName(String name) {
        return taskRepository.searchByName(name);
    }

    public Mono<Void> deleteById(Long id) {
        return taskRepository.deleteById(id);
    }

    public Flux<Task> linkTasks(TaskLinkRequest linkRequest) {
        return taskRepository.findById(linkRequest.getTaskIdFrom())
                .zipWith(taskRepository.findById(linkRequest.getTaskIdTo()))
                .flatMap(tasks -> {
                    Task taskFrom = tasks.getT1();
                    Task taskTo = tasks.getT2();
                    taskFrom.dependsOn(taskTo);
                    return taskRepository.save(taskFrom)
                            .thenReturn(List.of(taskFrom, taskTo));
                }).flatMapMany(Flux::fromIterable);
    }
}
