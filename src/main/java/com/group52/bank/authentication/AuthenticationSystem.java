package com.group52.bank.authentication;

import com.group52.bank.model.*;
import com.group52.bank.run_system.Main;

import javax.swing.text.html.HTML;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationSystem {

    private Map<String, User> users; // Stores username as key and User object as value
    private String parentCSV;
    private String childCSV;

    public AuthenticationSystem(String parentCSV, String childCSV) {
        this.users = new HashMap<>();
        this.parentCSV = parentCSV;
        this.childCSV = childCSV;
        loadUsersFromCSV(parentCSV);
        loadUsersFromCSV(childCSV);
    }
    public List<Child> loadChildrenData() {
        List<Child> children = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                String balance = data[2];
                children.add(new Child(username, password, Double.parseDouble(balance)));
            }
        } catch (IOException e) {
            System.err.println("Error loading children data from CSV: " + e.getMessage());
        }
        return children;
    }

    private void loadUsersFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                if (filename.equals(parentCSV)) {
                    users.put(username, new Parent(username, password));
                } else if (filename.equals(childCSV)) {
                    users.put(username, new Child(username, password));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users from CSV: " + e.getMessage());
        }
    }

    public Child findChildByUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[0].equals(username)) {
                    String password = data[1];
                    double balance = 0.0; // Default balance if not available
                    if (data.length >= 3) {
                        balance = Double.parseDouble(data[2]);
                    }
                    return new Child(username, password, balance);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error finding child by username: " + e.getMessage());
        }
        return null; // Child not found
    }


    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        users.put(user.getUsername(), user);
        try (FileWriter writer = new FileWriter(user instanceof Parent ? parentCSV : childCSV, true)) {
            writer.write(user.getUsername() + "," + user.getPassword());
            if (user instanceof Child) {
                writer.write(","+ 0.0);
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User login(String username, String password) {
        if (!users.containsKey(username)) {
            return null; // Username not found
        }
        User user = users.get(username);
        if (user.getPassword().equals("HASHED_" + password)) {
            System.out.println("\nSuccessfully login! Welcome, " + username + "!");
            return user;
        } else {
            return null; // Invalid password
        }
    }
}
