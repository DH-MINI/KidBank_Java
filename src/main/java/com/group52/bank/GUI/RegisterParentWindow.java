package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class represents the registration window for parents in the banking application.
 */
public class RegisterParentWindow extends JFrame {

    private AuthenticationSystem authSystem;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton cancelButton;

    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";
    private static final String profitRateCSV = "src/main/resources/datacsv/profitRate.csv";
    /**
     * Constructs a new RegisterParentWindow with the given authentication system.
     *
     * @param authSystem the authentication system
     */
    public RegisterParentWindow(AuthenticationSystem authSystem) {
        super("Parent Registration");
        this.authSystem = authSystem;

        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Parent Registration:",JLabel.CENTER);
        usernameLabel = new JLabel("Username:",JLabel.CENTER);
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:",JLabel.CENTER);
        passwordField = new JPasswordField(15);
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        registerButton.addActionListener(e -> handleRegister());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        JPanel jp01 = new JPanel();
        jp01.setBackground(Color.cyan);

        JPanel jp011 = new JPanel();
        JPanel jp012 = new JPanel();
        JPanel jp013 = new JPanel();
        JPanel jp014 = new JPanel();
        JPanel jp015 = new JPanel();
        JPanel jp016 = new JPanel();

        jp011.setBackground(Color.PINK);
        jp012.setBackground(Color.PINK);
        jp013.setBackground(Color.PINK);
        jp014.setBackground(Color.PINK);
        jp014.setBackground(Color.PINK);
        jp015.setBackground(Color.PINK);
        jp016.setBackground(Color.PINK);

        JPanel jp02 = new JPanel();
        jp02.setBackground(Color.DARK_GRAY);
        jp02.setOpaque(false);

        JPanel jp03 = new JPanel((new GridLayout(7, 2)));
        jp03.setBackground(Color.cyan);
        // Add Swing components to the frame
        jp03.add(titleLabel);
        jp03.add(jp01);
        jp03.add(jp011);
        jp03.add(jp012);
        jp03.add(usernameLabel);
        jp03.add(usernameField);
        jp03.add(jp013);
        jp03.add(jp014);
        jp03.add(passwordLabel);
        jp03.add(passwordField);
        jp03.add(jp015);
        jp03.add(jp016);
        jp03.add(registerButton);
        jp03.add(cancelButton);

        JLabel labImageBJ = new JLabel(new ImageIcon("src/main/resources/Image/bg01.png"),JLabel.CENTER);

        this.setLayout(new GridBagLayout());
        this.add(labImageBJ);
        this.add(jp02);
        this.add(jp03,new GridBagConstraints());

        // Set frame properties
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    /**
     * Handles the registration of a parent.
     */
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
