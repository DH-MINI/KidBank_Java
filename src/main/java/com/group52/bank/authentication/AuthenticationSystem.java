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
                children.add(new Child(username, password));
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

    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        users.put(user.getUsername(), user);
        try (FileWriter writer = new FileWriter(user instanceof Parent ? parentCSV : childCSV, true)) {
            writer.write(user.getUsername() + "," + user.getPassword() + "\n");
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
        String tmpPassword = "HASHED_" + password;
        if (user.getPassword().equals(tmpPassword)) {
            System.out.println("\nSuccessfully login! Welcome, " + username + "!");
            return user;
        } else {
            return null; // Invalid password
        }
    }
}
