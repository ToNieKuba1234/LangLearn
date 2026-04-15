package com.jakkas.langlearn.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jakkas.langlearn.restclient.UsersRestClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    private final UsersRestClient restClient;

    public LoginController(UsersRestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/process-login")
    public String handleLogin(@RequestParam String username, 
                              @RequestParam String password, 
                              HttpServletRequest request) {
        System.out.println("DEBUG: Attempted to log in: " + username);
        
        if (restClient.authenticate(username, password)) {
            var auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            
            System.out.println("DEBUG: Session created, redirecting to /");
            return "redirect:/";
        }
        return "redirect:/login?error";
    }
}