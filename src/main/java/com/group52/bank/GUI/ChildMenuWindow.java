package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;

public class ChildMenuWindow extends JFrame {
    private ChildrensBankingApp app;
    private Child child;
    private TransactionSystem transSystem;
    private TaskSystem taskSystem;

    private JLabel titleLabel;
    private JButton viewBalanceButton;
    private JButton viewTransactionHistoryButton;
    private JButton depositWithdrawButton;
    private JButton taskManagementButton;
    private JButton logoutButton;

    public ChildMenuWindow(ChildrensBankingApp app, Child child, TransactionSystem transSystem, TaskSystem taskSystem) {
        super("Child Menu - Welcome, " + child.getUsername());
        this.app = app;
        this.child = child;
        this.transSystem = transSystem;
        this.taskSystem = taskSystem;

        // Load background image
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Ensure layout manager of JLabel is null to freely position components
        backgroundLabel.setLayout(null);

        //getContentPane().setBackground(new Color(247, 246, 254)); // Set lavender background

        setLayout(new BorderLayout());

        // Title label setup
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 60px;'>Child Menu</span><br/>" +
                "<span style='font-size: 40px; color: orange;'>Welcome, " + child.getUsername() + "</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //buttonPanel.setOpaque(false); // Make the background transparent



        // Create a JPanel to hold buttons
        JPanel buttonContainer = new JPanel();
        buttonContainer.setOpaque(false); // Set the background to be transparent
        buttonContainer.setLayout(new GridLayout(5, 1, 0, 30)); // Use GridLayout for vertical arrangement with gaps
        backgroundLabel.add(buttonContainer); // Add the container to the background label

        // Create buttons
        viewBalanceButton = createColoredButton("View Balance");
        viewTransactionHistoryButton = createColoredButton("View Transaction History");
        depositWithdrawButton = createColoredButton("Deposit and Withdraw");
        taskManagementButton = createColoredButton("Task Management");
        logoutButton = createColoredButton("Logout");

        // Add buttons to the container
        buttonContainer.add(viewBalanceButton);
        buttonContainer.add(viewTransactionHistoryButton);
        buttonContainer.add(depositWithdrawButton);
        buttonContainer.add(taskManagementButton);
        buttonContainer.add(logoutButton);



        // Setup event handlers for buttons
        setupEventHandlers();

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add image to the left side
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/Image/childmenu1.png"));
        imageLabel.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupEventHandlers() {
        viewBalanceButton.addActionListener(e -> handleViewBalance());
        viewTransactionHistoryButton.addActionListener(e -> handleViewTransactionHistory());
        depositWithdrawButton.addActionListener(e -> handleDepositWithdraw());
        taskManagementButton.addActionListener(e -> handleChildTaskMenuWindow());
        logoutButton.addActionListener(e -> handleLogout());
    }

    private JButton createColoredButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24)); // Decrease font size
        button.setBackground(Color.ORANGE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 30)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }


    private void handleViewBalance() {
        JOptionPane.showMessageDialog(this, "Your balance is: $" + child.getBalance(), "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleViewTransactionHistory() {
        new ViewTransactionHistoryWindow(transSystem).setVisible(true);
    }

    private void handleDepositWithdraw() {
        new DepositWithdrawWindow(child, transSystem).setVisible(true);
    }

    private void handleChildTaskMenuWindow() {
        new ChildTaskMenuWindow(taskSystem, this, child).setVisible(true);
    }

    private void handleLogout() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            this.dispose();
            app.loginWindow.setVisible(true);
        }
    }
}
