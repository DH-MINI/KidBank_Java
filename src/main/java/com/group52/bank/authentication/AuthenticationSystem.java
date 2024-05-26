package com.group52.bank.authentication;

import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class represents the authentication system of the bank application.
 * It manages the users and their authentication.
 */
public class AuthenticationSystem {

    private final Map<String, User> users = new HashMap<>();
    private final String parentCSV;
    private final String childCSV;


    /**
     * Constructs an AuthenticationSystem with the given CSV files for parents and children.
     *
     * @param parentCSV the CSV file for parents
     * @param childCSV the CSV file for children
     */
    public AuthenticationSystem(String parentCSV, String childCSV) {
        this.parentCSV = parentCSV;
        this.childCSV = childCSV;
        loadUsersFromCSV(parentCSV, true);
        loadUsersFromCSV(childCSV, false);
    }

    /**
     * Loads users from a CSV file.
     *
     * @param filename the name of the CSV file
     * @param isParent true if the users are parents, false if they are children
     */
    private void loadUsersFromCSV(String filename, boolean isParent) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                User user = isParent ? new Parent(username, password, true) : new Child(username, password,true);
                if (data.length > 2 && !isParent) {
                    ((Child) user).setBalance(Double.parseDouble(data[2]));
                    ((Child) user).setSavingGoal(Double.parseDouble(data[3]));
                }
                users.put(username, user);
                System.out.println(username);
                System.out.println(password);

            }
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Error loading users from CSV: " + e.getMessage());
        }
    }

    /**
     * Loads children data from a CSV file.
     *
     * @return a list of children
     */

    public List<Child> loadChildrenData() {
        List<Child> children = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                double balance = data.length > 2 ? Double.parseDouble(data[2]) : 0.0;
                double savingGoal = data.length > 2 ? Double.parseDouble(data[3]) : 0.0;
                children.add(new Child(username, password, balance,true, savingGoal));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading children data from CSV: " + e.getMessage());
        }
        return children;
    }
    /**
     * Finds a child by their username.
     *
     * @param username the username of the child
     * @return the child if they exist, null otherwise
     */
    public Child findChildByUsername(String childname) {
//        User user = users.get(username);
//        if (user instanceof Child) {
//            return (Child) user;
//        }

        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                double balance = data.length > 2 ? Double.parseDouble(data[2]) : 0.0;
                double savingGoal = data.length > 2 ? Double.parseDouble(data[3]) : 0.0;
                if (username.equals(childname)) {
                    return new Child(username, password, balance, true, savingGoal);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading children data from CSV: " + e.getMessage());
        }
        return null;
    }
    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return true if the registration was successful, false otherwise
     */
    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        users.put(user.getUsername(), user);
        try (FileWriter writer = new FileWriter(user instanceof Parent ? parentCSV : childCSV, true)) {
            writer.write(user.getUsername() + "," + user.getPassword());
            if (user instanceof Child) {
                writer.write("," + ((Child) user).getBalance());
                writer.write("," + ((Child) user).getSavingGoal());
            }
            System.out.println(user.getPassword());
            writer.write("\n");
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return true;
    }
    /**
     * Logs in a user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the user if the login was successful, null otherwise
     */
    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && verifyPassword(user.getPassword(), password)) {
            System.out.println("\nSuccessfully login! Welcome, " + username + "!");
            return user;
        }
        return null; // Invalid username or password
    }
    /**
     * Verifies a password against a hashed password.
     *
     * @param hashedPassword the hashed password
     * @param inputPassword the input password
     * @return true if the passwords match, false otherwise
     */
    private boolean verifyPassword(String hashedPassword, String inputPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String hashedInputPassword = hexString.toString();
            System.out.println(hashedPassword);
            System.out.println(hashedInputPassword);
            return hashedPassword.equals(hashedInputPassword);
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            return false;
        }
    }
}
