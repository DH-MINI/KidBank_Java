package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class represents the login window in the banking application.
 */
public class LoginWindow extends JFrame {

    private ChildrensBankingApp app;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    public JPanel jp01;
    /**
     * Constructs a new LoginWindow with the given banking application.
     *
     * @param app the banking application
     */
    public LoginWindow(ChildrensBankingApp app) {
        super("Login");
        this.app = app;

        // Create Swing components and arrange them using a layout manager
        usernameLabel = new JLabel("Username:",JLabel.CENTER);
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:",JLabel.CENTER);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register (Parent)");

        //usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);


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
        JPanel jp01 = new JPanel();
        JPanel jp02 = new JPanel();
        JPanel jp03 = new JPanel();
        JPanel jp04 = new JPanel();
        JPanel jp05 = new JPanel();
        jp01.setBackground(Color.PINK);
        jp02.setBackground(Color.PINK);
        jp03.setBackground(Color.PINK);
        jp04.setBackground(Color.PINK);
        jp05.setBackground(Color.gray);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setPreferredSize(new Dimension(320, 160));
        panel.setBackground(Color.cyan);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(jp01);
        panel.add(jp02);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(jp03);
        panel.add(jp04);
        panel.add(loginButton);
        panel.add(registerButton);

        // Add panel to the frame
        JLabel labImageBJ = new JLabel(new ImageIcon("src/main/resources/Image/bg01.png"),JLabel.CENTER);

        this.setLayout(new GridBagLayout());
        this.add(labImageBJ,new GridBagConstraints());
        this.add(jp05);
        this.add(panel,new GridBagConstraints());

        // Set frame properties

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // 将窗口置于屏幕中央
        this.setVisible(true);
    }
    /**
     * Handles the login action.
     */
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
                new ChildMenuWindow(app, app.authSystem.findChildByUsername(username), app.transSystem, app.taskSystem, app.authSystem).setVisible(true);
            }
            this.setVisible(false);
        } else {
            // Login failed, display error message
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles the register parent action.
     */
    private void handleRegisterParent() {
        new RegisterParentWindow(app.authSystem).setVisible(true);
    }

}
