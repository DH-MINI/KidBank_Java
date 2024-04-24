package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterParentWindow extends JFrame {

    private AuthenticationSystem authSystem;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterParentWindow(AuthenticationSystem authSystem) {
        super("Parent Registration");
        this.authSystem = authSystem;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Parent Registration");
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        registerButton.addActionListener(e -> handleRegister());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 2));

        // Add Swing components to the frame
        add(titleLabel);
        add(new JLabel()); // Placeholder for empty cell
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);
        add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authSystem.register(new Parent(username, password))) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
