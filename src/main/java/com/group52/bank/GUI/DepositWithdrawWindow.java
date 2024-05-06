package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.model.TermDeposit;
import com.group52.bank.model.Transaction;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class DepositWithdrawWindow extends JFrame {

    private Child child;
    private TransactionSystem transSystem;

    private JLabel titleLabel;
    private JLabel amountLabel;
    private JTextField amountField;
    private JRadioButton depositRadioButton;
    private JRadioButton TDRadioButton;
    private JRadioButton withdrawRadioButton;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel balance;

    public DepositWithdrawWindow(Child child, TransactionSystem transSystem) {
        super("Deposit and Withdraw");
        this.child = child;
        this.transSystem = transSystem;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Deposit and Withdraw");
        amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        depositRadioButton = new JRadioButton("Deposit");
        TDRadioButton = new JRadioButton("Term Deposit");
        withdrawRadioButton = new JRadioButton("Withdraw");
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        balance = new JLabel("Account Balance: " + child.getBalance());

        // Set deposit radio button selected by default
        depositRadioButton.setSelected(true);

        // Create button group for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(depositRadioButton);
        buttonGroup.add(TDRadioButton);
        buttonGroup.add(withdrawRadioButton);

        // Add action listeners for buttons
        submitButton.addActionListener(e -> handleTransaction());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(5, 3));

        // Add Swing components to the frame
        add(titleLabel);
        add(new JLabel()); // Placeholder
        add(balance);
        add(new JLabel());
        add(amountLabel);
        add(amountField);
        add(depositRadioButton);
        add(TDRadioButton);
        add(withdrawRadioButton);
        add(submitButton);
        add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleTransaction() {
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String transactionId = "TRANS_" + System.currentTimeMillis();
        String transactionSource = "Bank";
        String transactionDestination = child.getUsername();
        String transactionState = "Unchecked";

        if (depositRadioButton.isSelected()) {
            // Deposit
            Transaction depositTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Deposit", transactionSource, transactionDestination, transactionState);
            transSystem.addTransaction(depositTransaction);
            JOptionPane.showMessageDialog(this, "Deposit request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (TDRadioButton.isSelected()) {
            new TDcheckDetailWindow(child, transSystem, amount);
        } else if (withdrawRadioButton.isSelected()) {
            // Withdraw
            if (amount > child.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Transaction withdrawalTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Withdrawal", transactionDestination, transactionSource, transactionState);
                transSystem.addTransaction(withdrawalTransaction);
                JOptionPane.showMessageDialog(this, "Withdrawal request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select Deposit or Withdraw.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        transSystem.saveTransactionHistory();
        this.dispose(); // Close window after transaction
    }
}
