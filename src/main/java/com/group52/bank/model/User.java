package com.group52.bank.model;

public abstract class User {

    protected String username;
    protected String password; // Hashed password (implement secure hashing)

    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password); // Hash password before storing
    }

    public String getUsername() {
        return username;
    }

    public abstract boolean login(); // Implemented in subclasses

    private String hashPassword(String plainTextPassword) {
        // Implement a secure password hashing algorithm (replace with actual implementation)
        return "HASHED_" + plainTextPassword; // Placeholder for demonstration
    }

    public String getPassword() {
        return  hashPassword(password);
    }

    // Getters and setters for username (optional)
}
