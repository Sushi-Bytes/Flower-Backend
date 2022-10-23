package com.sushi.flower.model;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node("Task")
@Getter
public class TaskEntity {
    @Id
    private final String name;
    @Property
    private final String description;
    @Relationship(type = "DEPENDS_ON", direction = INCOMING)
    private Set<TaskEntity> dependencies = new HashSet<>();
    public TaskEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
