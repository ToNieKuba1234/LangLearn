package com.jakkas.langlearn.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jakkas.langlearn.model.User;
import com.jakkas.langlearn.repository.UsersRepository;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UsersService(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    public boolean checkLogin(String username, String rawPassword) {
        Optional<User> userOpt = usersRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public boolean registerUser(User user) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) return false;

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        usersRepository.save(user);
        return true;
    }
}