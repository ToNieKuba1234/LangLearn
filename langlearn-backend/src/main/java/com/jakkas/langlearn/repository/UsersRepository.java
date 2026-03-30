package com.jakkas.langlearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jakkas.langlearn.model.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}