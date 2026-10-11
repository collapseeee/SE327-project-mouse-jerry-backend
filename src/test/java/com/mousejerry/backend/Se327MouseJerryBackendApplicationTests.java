package com.mousejerry.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DockerAvailableCondition.class)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Se327MouseJerryBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
