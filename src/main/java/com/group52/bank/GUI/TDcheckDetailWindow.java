package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.model.TermDeposit;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * This class represents the window for checking the details of a term deposit in the banking application.
 */
public class TDcheckDetailWindow extends JFrame {
    private Child child;
    private TransactionSystem trans;
    private double amount;

    private JLabel TimeLabel, blank;
    private JButton submitButton, cancelButton;

    private JTextField TimeField;
    /**
     * Constructs a new TDcheckDetailWindow with the given child, transaction system, and amount.
     *
     * @param child the child user
     * @param trans the transaction system
     * @param amount the amount for the term deposit
     */
    public TDcheckDetailWindow(Child child, TransactionSystem trans, double amount){
        super("Checking details of Term Deposit");

        this.child = child;
        this.trans = trans;
        this.amount = amount;

        TimeLabel = new JLabel("Months:");
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        blank = new JLabel("           ");

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setForeground(Color.GREEN);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setForeground(Color.RED);

        TimeField = new JTextField(10);
        TimeField.setFont(new Font("Arial", Font.PLAIN, 14));

        submitButton.addActionListener(e -> handler());
        cancelButton.addActionListener(e -> this.dispose());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("The current profit rate is " + trans.getCurrentProfitRate() + " per months"), gbc);

        gbc.gridy = 1;
        panel.add(TimeLabel, gbc);

        gbc.gridy = 2;
        panel.add(TimeField, gbc);

        gbc.gridy = 3;
        panel.add(submitButton, gbc);

        gbc.gridy = 4;
        panel.add(cancelButton, gbc);

        this.getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    /**
     * Handles the action of submitting the term deposit details.
     *
     * @return true if the details are submitted successfully, false otherwise
     */
    private boolean handler(){
        int months = 0;

        try {
            months = Integer.parseInt(TimeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String transactionId = "TRANS_" + System.currentTimeMillis();
        String transactionSource = "Bank";
        String transactionDestination = child.getUsername();
        String transactionState = "Unchecked";
        LocalDate due = LocalDate.now().plusMonths(months);

        trans.addTransaction(new TermDeposit(transactionId, this.amount, LocalDateTime.now(), "TD", transactionSource, transactionDestination, transactionState, due, trans.getCurrentProfitRate(), months));

        this.dispose();
        JOptionPane.showMessageDialog(this, "Time Deposit request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
