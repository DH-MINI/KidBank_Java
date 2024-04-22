package com.group52.bank.authentication;

import com.group52.bank.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSystem {

    private Map<String, User> users; // Stores username as key and User object as value
    private String userDataFilePath; // Path to the file containing user data

    public AuthenticationSystem(String userDataFilePath) {
        this.users = new HashMap<>();
        this.userDataFilePath = userDataFilePath;
        loadUsersFromCSV(userDataFilePath); // Load users from CSV upon initialization
    }

    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        users.put(user.getUsername(), user);
        return true;
    }

    public User login(String username, String password) {
        if (!users.containsKey(username)) {
            return null; // Username not found
        }
        User user = users.get(username);
        if (user.login()) { // Delegate login logic to the specific User subclass
            return user;
        } else {
            return null; // Invalid password
        }
    }

    // Load user data from CSV file
    private void loadUsersFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(","); // Assuming CSV format: username,password,userType
                String username = userData[0];
                String password = userData[1];
                String userType = userData[2]; // Assuming the third column denotes the user type
                // Create User object based on userType (you need to implement this)
                // Example: if (userType.equals("Parent")) { users.put(username, new Parent(username, password)); }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
