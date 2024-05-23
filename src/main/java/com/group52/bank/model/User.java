package com.group52.bank.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This abstract class represents a User in the banking application.
 * A User has a username and a hashed password.
 */
public abstract class User {

    protected String username;
    protected String password; // Hashed password
    /**
     * Constructs a new User with the given username and password.
     *
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password); // Hash password before storing
    }
    /**
     * Returns the username of the User.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Hashes the given plain text password.
     *
     * @param plainTextPassword the plain text password
     * @return the hashed password
     */
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
    /**
     * Returns the password of the User.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}

