package com.sushi.flower.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

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
    @Relationship(type = "DEPENDS_ON", direction = INCOMING)
    private Set<Task> dependencies = new HashSet<>();
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void dependsOn(Task task) {
        dependencies.add(task);
    }
}
