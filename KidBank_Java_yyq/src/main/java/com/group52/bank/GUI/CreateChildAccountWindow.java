package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Child;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CreateChildAccountWindow extends JFrame {

    private AuthenticationSystem authSystem;
    private ParentMenuWindow parentMenuWindow;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton createButton;
    private JButton cancelButton;

    public CreateChildAccountWindow(AuthenticationSystem authSystem, ParentMenuWindow parentMenuWindow) {
        super("Create Child Account");
        this.authSystem = authSystem;
        this.parentMenuWindow = parentMenuWindow;

        // Set layout manager for the frame
        setLayout(new GridLayout(5, 2));

        // Create Swing components
        // titleLabel = new JLabel("Create Child Account");
        // add(titleLabel);

        usernameLabel = new JLabel("Username:");
        add(usernameLabel);
        usernameField = new JTextField(15);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordField = new JPasswordField(15);
        add(passwordField);

        createButton = new JButton("Create Account");
        add(createButton);
        createButton.addActionListener(e -> handleCreateAccount());

        cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleCreateAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Perform child registration using AuthenticationSystem
        if (authSystem.register(new Child(username, password))) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            this.dispose(); // Close window on success
//            parentMenuWindow.refreshChildrenList(); // Optional: Update children list in parent menu

        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
