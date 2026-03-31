package com.jakkas.langlearn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jakkas.langlearn.repository.UsersRepository;
import com.jakkas.langlearn.service.UsersService;

@SpringBootApplication
public class LangLearnBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LangLearnBackendApplication.class, args);
    }

    // Tworzenie testowego konta
    @Bean
    public CommandLineRunner initData(UsersService usersService, UsersRepository repository) {
        return args -> {
            if (repository.findByUsername("jakk").isEmpty()) {
                usersService.registerUser("jakk", "kuba123");
                System.out.println("Test account created successfully: jakk / kuba123");
            }
        };
    }
}