package com.group52.bank.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class User {

    protected String username;
    protected String password; // Hashed password

    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password); // Hash password before storing
    }

    public String getUsername() {
        return username;
    }

    String hashPassword(String plainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainTextPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();

            return null;
        }
    }

    public String getPassword() {
        return password;
    }
}

