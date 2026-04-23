package com.jakkas.langlearn.dto;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private Integer trophies = 0;

    public User() {}

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

        return Objects.equals(other.username, username)
            && Objects.equals(other.password, password)
            && Objects.equals(other.trophies, trophies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            username,
            password,
            trophies
        );
    }
}