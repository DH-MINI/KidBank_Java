package com.group52.bank.GUI;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;

public class ChangeTransactionStateWindow extends JFrame {

    private TransactionSystem transSystem;
    private TransactionMenuWindow transactionMenuWindow;

    private JLabel titleLabel;
    private JLabel transactionIdLabel;
    private JTextField transactionIdField;
    private JLabel newStateLabel;
    private JComboBox<String> newStateComboBox;
    private JButton submitButton;
    private JButton cancelButton;

    public ChangeTransactionStateWindow(TransactionSystem transSystem, TransactionMenuWindow transactionMenuWindow) {
        super("Change Transaction State");
        this.transSystem = transSystem;
        this.transactionMenuWindow = transactionMenuWindow;

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 2));

        transactionIdLabel = new JLabel("Transaction ID:");
        add(transactionIdLabel);
        transactionIdField = new JTextField(15);
        add(transactionIdField);

        newStateLabel = new JLabel("New State:");
        add(newStateLabel);
        newStateComboBox = new JComboBox<>(new String[]{"Confirmed", "Rejected"});
        add(newStateComboBox);

        submitButton = new JButton("Submit");
        add(submitButton);
        submitButton.addActionListener(e -> handleChangeState());

        cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleChangeState() {
        String transactionId = transactionIdField.getText();
        String newState = (String) newStateComboBox.getSelectedItem();

        if (transSystem.changeTransactionState(transactionId, newState)) {
            JOptionPane.showMessageDialog(this, "Transaction state changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
            // transactionMenuWindow.refreshTransactionHistory(); // Optional: Update transaction list in parent menu
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change transaction state. Transaction ID not found or invalid state.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
