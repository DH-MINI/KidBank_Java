package com.group52.bank.GUI;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;

public class TransactionMenuWindow extends JFrame {

    private TransactionSystem transSystem;
    private ParentMenuWindow parentMenuWindow;

    private JLabel titleLabel;
    private JButton viewHistoryButton;
    private JButton changeStateButton;
    private JButton backButton;

    public TransactionMenuWindow(TransactionSystem transSystem, ParentMenuWindow parentMenuWindow) {
        super("Transaction Management");
        this.transSystem = transSystem;
        this.parentMenuWindow = parentMenuWindow;

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 1));

        // Create Swing components
        titleLabel = new JLabel("Transaction Management");
        add(titleLabel);

        viewHistoryButton = new JButton("View Transaction History");
        add(viewHistoryButton);
        viewHistoryButton.addActionListener(e -> handleViewTransactionHistory());

        changeStateButton = new JButton("Change Transaction State");
        add(changeStateButton);
        changeStateButton.addActionListener(e -> handleChangeTransactionState());

        backButton = new JButton("Back");
        add(backButton);
        backButton.addActionListener(e -> this.dispose()); // Close window on back

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleViewTransactionHistory() {

        transSystem.viewTransactionHistory(); // Call existing method for console output (optional)
        new ViewTransactionHistoryWindow(transSystem).setVisible(true); // Open separate window for detailed history
    }

    private void handleChangeTransactionState() {
        if (transSystem.viewUncheckedTransactionHistory()) {
            // Open a separate window for selecting transaction and state change
            new ChangeTransactionStateWindow(transSystem, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No unreviewed transactions found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}


