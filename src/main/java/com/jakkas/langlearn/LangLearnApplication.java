package com.jakkas.langlearn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import com.jakkas.langlearn.repository.FlashcardRepository;
import com.jakkas.langlearn.repository.UsersRepository;
import com.jakkas.langlearn.service.UsersService;

@SpringBootApplication
@Controller
public class LangLearnApplication {

    private final FlashcardRepository repository;

    public LangLearnApplication(FlashcardRepository repository) {
        this.repository = repository;
    }
    public static void main(String[] args) {
        SpringApplication.run(LangLearnApplication.class, args);
    }


    //create an test account
    @Bean
    public CommandLineRunner initData(UsersService usersService, UsersRepository repository) {
        return args -> {
            if (repository.findByUsername("jakk").isEmpty()) {
                usersService.registerUser("jakk", "kuba123");
                System.out.println("Test account created succesfully: jakk / kuba123");
            }
        };
    }
}