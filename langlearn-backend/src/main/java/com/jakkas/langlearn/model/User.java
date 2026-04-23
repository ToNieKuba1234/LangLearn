package com.jakkas.langlearn.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;
    
    @Column(name = "trophies", nullable = false)
    private Integer trophies = 0;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getTrophies() { return trophies; }
    public void setTrophies(Integer trophies) { this.trophies = trophies; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;

        return Objects.equals(other.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id
        );
    }
}