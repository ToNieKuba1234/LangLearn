package com.jakkas.langlearn.controller;

import java.util.NoSuchElementException;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jakkas.langlearn.dto.User;
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
        String currentUsername = "";
        if (auth != null) { currentUsername = auth.getName(); }

        User userDetails = usersRestClient.getUserDetails(currentUsername);

        try {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("trophies", userDetails.getTrophies());
        } catch(Exception e) {
            throw new NoSuchElementException("Unable to find user details for : " + currentUsername);
        }

        return "index"; 
    }
}