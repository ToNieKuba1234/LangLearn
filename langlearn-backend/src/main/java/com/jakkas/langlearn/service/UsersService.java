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

    public User registerUser(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        return usersRepository.save(newUser);
    }

    public boolean checkLogin(String username, String rawPassword) {
        Optional<User> userOpt = usersRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}