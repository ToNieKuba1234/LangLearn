package com.jakkas.langlearn.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jakkas.langlearn.model.Users;
import com.jakkas.langlearn.repository.UsersRepository;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UsersService(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    public Users registerUser(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        return usersRepository.save(newUser);
    }

    public boolean checkLogin(String username, String rawPassword) {
        Optional<Users> userOpt = usersRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}