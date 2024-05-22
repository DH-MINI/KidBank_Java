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

public class AuthenticationSystem {

    private final Map<String, User> users = new HashMap<>();
    private final String parentCSV;
    private final String childCSV;

    public AuthenticationSystem(String parentCSV, String childCSV) {
        this.parentCSV = parentCSV;
        this.childCSV = childCSV;
        loadUsersFromCSV(parentCSV, true);
        loadUsersFromCSV(childCSV, false);
    }

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

    public Child findChildByUsername(String username) {
        User user = users.get(username);
        if (user instanceof Child) {
            return (Child) user;
        }
        return null;
    }

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

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && verifyPassword(user.getPassword(), password)) {
            System.out.println("\nSuccessfully login! Welcome, " + username + "!");
            return user;
        }
        return null; // Invalid username or password
    }

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
