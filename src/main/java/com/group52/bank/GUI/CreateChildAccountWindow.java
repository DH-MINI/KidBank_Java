package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Child;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class represents the window for creating a child account in the banking application.
 */
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
    /**
     * Constructs a new CreateChildAccountWindow with the given authentication system and parent menu window.
     *
     * @param authSystem the authentication system
     * @param parentMenuWindow the parent menu window
     */
    public CreateChildAccountWindow(AuthenticationSystem authSystem, ParentMenuWindow parentMenuWindow) {
        super("Create Child Account");
        this.authSystem = authSystem;
        this.parentMenuWindow = parentMenuWindow;

        usernameLabel = new JLabel("Username:",JLabel.CENTER);
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:",JLabel.CENTER);
        passwordField = new JPasswordField(15);
        createButton = new JButton("Create Account");
        cancelButton = new JButton("Cancel");
        createButton.addActionListener(e -> handleCreateAccount());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        JPanel jp01 = new JPanel();
        JPanel jp02 = new JPanel();
        JPanel jp03 = new JPanel();
        JPanel jp04 = new JPanel();
        JPanel jp05 = new JPanel();
        JPanel jp06 = new JPanel();
        JPanel jp07 = new JPanel();
        JPanel jp08 = new JPanel();

        jp01.setBackground(Color.PINK);
        jp02.setBackground(Color.PINK);
        jp03.setBackground(Color.PINK);
        jp04.setBackground(Color.PINK);
        jp05.setBackground(Color.PINK);
        jp06.setBackground(Color.PINK);
        jp07.setBackground(Color.gray);
        jp08.setBackground(Color.cyan);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.setPreferredSize(new Dimension(320, 180));

        panel.setBackground(Color.cyan);
        panel.add(new JLabel("Create Child Account:",JLabel.CENTER));
        panel.add(jp08);
        panel.add(jp05);
        panel.add(jp06);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(jp01);
        panel.add(jp02);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(jp03);
        panel.add(jp04);
        panel.add(createButton);
        panel.add(cancelButton);

        // Add panel to the frame
        JLabel labImageBJ = new JLabel(new ImageIcon("src/main/resources/Image/bg01.png"),JLabel.CENTER);

        this.setLayout(new GridBagLayout());
        this.add(labImageBJ,new GridBagConstraints());
        this.add(jp07);
        this.add(panel,new GridBagConstraints());

        // Set frame properties

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    /**
     * Handles the creation of a new child account.
     */
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
