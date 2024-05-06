package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.model.User;

import javax.swing.*;

import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private ChildrensBankingApp app;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginWindow(ChildrensBankingApp app) {
        super("Login");
        this.app = app;

        // Create Swing components and arrange them using a layout manager
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register (Parent)");

        // Add action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegisterParent();
            }
        });

        // Create a panel with GridLayout to arrange components
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        // Add panel to the frame
        this.add(panel);

        // Set frame properties
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void handleLogin() {
        // Get username and password from text fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Perform login logic using AuthenticationSystem
        User user = app.authSystem.login(username, password);

        if (user != null) {
            // Login successful, navigate to appropriate window
            if (user instanceof Parent) {
                new ParentMenuWindow(app, (Parent) user, app.transSystem, app.taskSystem).setVisible(true);
            } else if (user instanceof Child) {
                new ChildMenuWindow(app, app.authSystem.findChildByUsername(username), app.transSystem, app.taskSystem).setVisible(true);
            }
            this.setVisible(false);
        } else {
            // Login failed, display error message
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegisterParent() {
        new RegisterParentWindow(app.authSystem).setVisible(true);
    }

}
