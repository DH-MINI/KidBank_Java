package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.transaction.TransactionSystem;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Child Menu - Welcome, " + child.getUsername());
        viewBalanceButton = new JButton("View Balance");
        viewTransactionHistoryButton = new JButton("View Transaction History");
        depositWithdrawButton = new JButton("Deposit and Withdraw");
        taskManagementButton = new JButton("Task Management");
        logoutButton = new JButton("Logout");

        // Add action listeners for buttons
        viewBalanceButton.addActionListener(e -> handleViewBalance());
        viewTransactionHistoryButton.addActionListener(e -> handleViewTransactionHistory());
        depositWithdrawButton.addActionListener(e -> handleDepositWithdraw());
        taskManagementButton.addActionListener(e -> handleChildTaskMenuWindow());
        logoutButton.addActionListener(e -> handleLogout());

        // Set layout manager for the frame
        setLayout(new GridLayout(6, 1));

        // Add Swing components to the frame
        add(titleLabel);
        add(viewBalanceButton);
        add(viewTransactionHistoryButton);
        add(depositWithdrawButton);
        add(taskManagementButton);
        add(logoutButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleViewBalance() {
        JOptionPane.showMessageDialog(this, "Your balance is: $" + child.getBalance(), "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleViewTransactionHistory() {
        // Open a separate window for viewing transaction history
        new ViewTransactionHistoryWindow(transSystem).setVisible(true);
    }

    private void handleDepositWithdraw() {
        // Open a separate window for deposit and withdraw
        new DepositWithdrawWindow(child, transSystem).setVisible(true);
    }

    private void handleChildTaskMenuWindow() {
        // Open a separate window for task management
        new ChildTaskMenuWindow(taskSystem, this, child).setVisible(true);
    }

    private void handleLogout() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            this.dispose(); // Close current window
            app.loginWindow.setVisible(true); // Show login window again
        }
    }
}
