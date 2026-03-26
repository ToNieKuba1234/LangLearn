package com.jakkas.langlearn.controller;

import com.jakkas.langlearn.model.Users;
import com.jakkas.langlearn.repository.UsersRepository;
import com.jakkas.langlearn.service.UsersService;
import jakarta.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/process-login")
    public String loginProcess(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model, 
                               HttpSession session) {
        
        System.out.println("DEBUG: Próba logowania dla: " + username);
        
        boolean isAuthenticated = usersService.checkLogin(username, password);
        
        if (isAuthenticated) {
            // 1. Tworzymy obiekt autoryzacji (nasz bilet wstępu)
            UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            
            // 2. Tworzymy nowy kontekst bezpieczeństwa
            SecurityContext sc = SecurityContextHolder.createEmptyContext();
            sc.setAuthentication(auth);
            SecurityContextHolder.setContext(sc);
            
            // 3. KLUCZOWE: Zapisujemy ten kontekst w sesji, żeby Spring go widział po przekierowaniu
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
            
            System.out.println("SUKCES: Użytkownik " + username + " zalogowany pomyślnie!");
            return "redirect:/";
        } else {
            System.out.println("BŁĄD: Niepoprawne dane logowania dla: " + username);
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        usersService.registerUser(username, password);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        //gets username from the currentcontext of SecuirtyContextHolder
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Users user = usersRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("username", user.getUsername());
        model.addAttribute("trophies", user.getTrophies());

        return "index";
    }
}