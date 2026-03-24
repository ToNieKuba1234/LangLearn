package com.jakkas.langlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fiszki", repository.findAll());
        return "index";
    }
}