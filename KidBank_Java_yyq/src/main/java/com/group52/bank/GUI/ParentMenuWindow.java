package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import com.group52.bank.model.Child;
import com.group52.bank.model.Parent;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParentMenuWindow extends JFrame {

    private ChildrensBankingApp app;
    private Parent parent;
    private TransactionSystem transSystem;
    private TaskSystem taskSystem;

    private JLabel welcomeLabel;
    private JButton viewChildrenButton;
    private JButton createChildAccountButton;
    private JButton transactionManagementButton;
    private JButton taskManagementButton;
    private JButton logoutButton;

    private JTable childTable;

    public ParentMenuWindow(ChildrensBankingApp app, Parent parent, TransactionSystem transSystem, TaskSystem taskSystem) {
        super("Parent Menu - Welcome, " + parent.getUsername());
        this.app = app;
        this.parent = parent;
        this.transSystem = transSystem;
        this.taskSystem = taskSystem;

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Create welcome label
        welcomeLabel = new JLabel("Welcome, " + parent.getUsername() + "!");
        add(welcomeLabel, BorderLayout.NORTH);

        // Create buttons
        viewChildrenButton = new JButton("View Children");
        createChildAccountButton = new JButton("Create Child Account");
        transactionManagementButton = new JButton("Transaction Management");
        taskManagementButton = new JButton("Task Management");
        logoutButton = new JButton("Logout");

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(viewChildrenButton);
        buttonPanel.add(createChildAccountButton);
        buttonPanel.add(transactionManagementButton);
        buttonPanel.add(taskManagementButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        viewChildrenButton.addActionListener(e -> handleViewChildren());
        createChildAccountButton.addActionListener(e -> handleCreateChildAccount());
        transactionManagementButton.addActionListener(e -> handleTransactionManagement());
        taskManagementButton.addActionListener(e -> handleTaskManagementSubMenu());
        logoutButton.addActionListener(e -> handleLogout());

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleViewChildren() {
        List<Child> children = new ArrayList<>(app.authSystem.loadChildrenData());
        if (children.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No children registered yet.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Create a 2D array of child information (username, balance, etc.)
            String[][] childData = new String[children.size()][];
            for (int i = 0; i < children.size(); i++) {
                Child child = children.get(i);
                childData[i] = new String[]{child.getUsername(), String.valueOf(child.getBalance())}; // Add more elements as needed
            }

            // Create column names for the table
            String[] columnNames = {"Username", "Balance"};

            // Create a JTable model and set the data and column names
            TableModel tableModel = new DefaultTableModel(childData, columnNames);
            childTable = new JTable(tableModel);

            // Optional: Adjust table column widths
//            childTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBCOLUMNS);

            // Add the table to a JScrollPane if needed
            JScrollPane scrollPane = new JScrollPane(childTable);
            JOptionPane.showMessageDialog(this, scrollPane, "Children List", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void handleCreateChildAccount() {
        // Open a separate window for child registration
        new CreateChildAccountWindow(app.authSystem, this).setVisible(true);
    }

    private void handleTransactionManagement() {
        // Open a separate window for transaction management sub-menu
        new TransactionMenuWindow(app.transSystem, this).setVisible(true);
    }

    private void handleTaskManagementSubMenu() {
        // Open a separate window for task management sub-menu
        new TaskMenuWindow(app.taskSystem, this).setVisible(true);
    }

    private void handleLogout() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            app.transSystem.saveTransactionHistory();
            this.dispose(); // Close current window
            app.loginWindow.setVisible(true); // Show login window again
        }
    }
}

