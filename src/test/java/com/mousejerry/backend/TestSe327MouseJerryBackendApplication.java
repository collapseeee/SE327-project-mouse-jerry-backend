package com.mousejerry.backend;

import org.springframework.boot.SpringApplication;

public class TestSe327MouseJerryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(Se327MouseJerryBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
