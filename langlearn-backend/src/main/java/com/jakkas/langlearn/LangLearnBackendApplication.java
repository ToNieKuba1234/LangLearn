package com.jakkas.langlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LangLearnBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LangLearnBackendApplication.class, args);
    }
}