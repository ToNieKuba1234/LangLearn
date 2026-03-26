package com.jakkas.langlearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;
    
    @Column(name = "trophies", nullable = false)
    private Integer trophies = 0;


    //username 
    public String getUsername() {
        return username; 
    }
    public void setUsername(String username) {
        this.username = username; 
    }

    
    //password
    public String getPassword() {
        return password;   
    }
    public void setPassword(String password) {
        this.password = password; 
    }

    //trophies
    public Integer getTrophies() {
        return trophies;
    }
    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }
}