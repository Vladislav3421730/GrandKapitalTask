package com.example.task;

import org.springframework.boot.SpringApplication;

public class TestGrandKapitalTaskApplication {

    public static void main(String[] args) {
        SpringApplication.from(GrandKapitalTaskApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
