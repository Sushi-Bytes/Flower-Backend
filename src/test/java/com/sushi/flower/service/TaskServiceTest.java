package com.sushi.flower.service;

import com.sushi.flower.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@Testcontainers
class TaskServiceTest {
    private static final String PASSWORD = "flower";
    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:" + env("NEO4J_VERSION", "4.4.5"))
            .withAdminPassword(PASSWORD);

    @Autowired
    TaskService taskService;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> PASSWORD);
        registry.add("spring.data.neo4j.database", () -> "neo4j");
    }

    @BeforeEach
    void setup(@Autowired Driver driver) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (n) DETACH DELETE n");
                tx.run(""
                        + "CREATE (task1:Task {name:'Test', description: 'Test Description', status: 0})\n"
                        + "CREATE (task2:Task {name:'Test 2', description: 'Test Description 2', status: 0})\n"
                        + "CREATE (task1)-[:DEPENDS_ON]->(task2)");
                return null;
            });
        }
    }

    @Test
    public void searches_movies_by_title() {
        String name = "Test";
        List<Task> taskResults = taskService.searchByName(name).collectList().block();
        assertEquals(2, taskResults.size());
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}