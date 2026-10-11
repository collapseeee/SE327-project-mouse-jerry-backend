package com.mousejerry.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(DockerAvailableCondition.class)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Se327MouseJerryBackendApplicationTests {

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context should load successfully");
    }

    @Test
    void springSessionTableExists() {
        if (jdbcTemplate != null) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM spring_session", Integer.class);
            assertNotNull(count, "Spring session count should not be null");
        }
    }
}

