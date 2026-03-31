package com.jakkas.langlearn.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jakkas.langlearn.dto.Users;
import com.jakkas.langlearn.restclient.UsersRestClient;

@Controller
public class IndexController {

    private final UsersRestClient usersRestClient;

    public IndexController(UsersRestClient usersRestClient) {
        this.usersRestClient = usersRestClient;
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Users userDetails = usersRestClient.getUserDetails(currentUsername);

        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("trophies", userDetails.getTrophies());
        } else {
            model.addAttribute("username", currentUsername);
            model.addAttribute("trophies", 0);
        }

        return "index"; 
    }
}