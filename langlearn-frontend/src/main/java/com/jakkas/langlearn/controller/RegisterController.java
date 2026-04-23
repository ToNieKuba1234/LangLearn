package com.jakkas.langlearn.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jakkas.langlearn.restclient.UsersRestClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {
    private UsersRestClient restClient;

    public RegisterController(UsersRestClient restClient) { this.restClient = restClient; }
    
    @GetMapping("/register")
    public String showRegisterPage() { return "register"; }

    @PostMapping("/process-register")
    public String handleRegister(@RequestParam String username, 
                                 @RequestParam String password, 
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("DEBUG: Registering User : " + username);

        boolean isRegistered = restClient.register(username, password);

        if(isRegistered) {
            if (restClient.authenticate(username, password)) {
                var auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                
                System.out.println("DEBUG: Session created, redirecting to /");
                return "redirect:/";
            }
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Użytkownik o podanej nazwie już istnieje!");
        return "redirect:/register?error";
    }

}