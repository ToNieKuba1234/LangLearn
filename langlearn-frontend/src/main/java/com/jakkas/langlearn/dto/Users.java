package com.jakkas.langlearn.dto;

public class Users {
    private String username;
    private String password;
    private Integer trophies = 0;

    public Users() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getTrophies() { return trophies; }
    public void setTrophies(Integer trophies) { this.trophies = trophies; }
}