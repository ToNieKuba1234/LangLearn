package com.jakkas.langlearn.controller;

import com.jakkas.langlearn.model.Users;
import com.jakkas.langlearn.repository.UsersRepository;
import com.jakkas.langlearn.service.UsersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class BackendUsersController {

    private UsersService usersService;
    private UsersRepository usersRepository;

    public BackendUsersController(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }
    
    // Pobieranie listy użytkowników (używamy Twojej klasy Users)
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return usersRepository.findAll(); 
    }

    //Find users by name
    @GetMapping("/users/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        return usersRepository.findByUsername(username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    

    // Logowanie - zwracamy status OK lub błąd, nie robimy przekierowań!
    @PostMapping("/process-login")
    public ResponseEntity<Void> loginProcess(@RequestBody Users loginData) {
        System.out.println("DEBUG BACKEND: Received login request for : " + loginData.getPassword());
        boolean isAuthenticated = usersService.checkLogin(loginData.getUsername(), loginData.getPassword());

        System.out.println("DEBUG BACKEND: Password verification result : " + isAuthenticated);
        if (isAuthenticated) {
            return ResponseEntity.ok().build(); // 200 OK - Backend mówi: "Dane są poprawne"
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 - Błędne dane
        }
    }
}