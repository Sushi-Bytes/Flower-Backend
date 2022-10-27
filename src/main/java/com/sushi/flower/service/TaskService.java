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
        return taskRepository.findById(linkRequest.getParentTask())
                .zipWith(taskRepository.findById(linkRequest.getChildTask()))
                .flatMap(tasks -> {
                    Task parentTask = tasks.getT1();
                    Task childTask = tasks.getT2();

                    childTask.dependsOn(parentTask);

                    return taskRepository.save(childTask)
                            .thenReturn(List.of(parentTask, childTask));
                }).flatMapMany(Flux::fromIterable);
    }

    public Flux<Task> unlinkTasks(TaskLinkRequest linkRequest) {
        return taskRepository.findById(linkRequest.getParentTask())
                .zipWith(taskRepository.findById(linkRequest.getChildTask()))
                .flatMap(tasks -> {
                    Task parentTask = tasks.getT1();
                    Task childTask = tasks.getT2();

                    childTask.independentFrom(parentTask);

                    return taskRepository.save(childTask)
                            .thenReturn(List.of(parentTask, childTask));
                }).flatMapMany(Flux::fromIterable);
    }
}
