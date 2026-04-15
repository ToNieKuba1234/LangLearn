package com.jakkas.langlearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jakkas.langlearn.model.User;


public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}