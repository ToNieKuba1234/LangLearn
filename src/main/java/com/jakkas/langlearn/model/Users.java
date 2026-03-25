package com.jakkas.langlearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data //auto getters/setters
public class Users {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    public String getUsername() {return username; }
    public String getPassword() {return password; }
    public void setUsername(String username) {this.username = username; }
    public void setPassword(String password) {this.password = password; }
}