package com.univer.public_transport_guide.model;

public class User {
    private String name;
    private String email;
    private String password;
    private int role;

    public User() {
    }

    public User(String name, String email, String password, int role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
