package com.sushi.flower.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Task")
@Getter
@EqualsAndHashCode
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private final String name;
    @Property
    private final String description;
    @Relationship(type = "NEXT_TASK", direction = OUTGOING)
    private Set<Task> nextTasks = new HashSet<>();

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addNextTask(Task task) {
        nextTasks.add(task);
    }

    public void removeNextTask(Task task) {
        nextTasks.remove(task);
    }
}
