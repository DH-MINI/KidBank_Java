package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.model.TermDeposit;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TDcheckDetailWindow extends JFrame {
    private Child child;
    private TransactionSystem trans;
    private double amount;

    private JLabel TimeLabel, blank;
    private JButton submitButton, cancelButton;

    private JTextField TimeField;

    public TDcheckDetailWindow(Child child, TransactionSystem trans, double amount){
        super("Checking details of Term Deposit");

        this.child = child;
        this.trans = trans;
        this.amount = amount;

        TimeLabel = new JLabel("Months:");
        blank = new JLabel("           ");

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        TimeField = new JTextField(10);

        submitButton.addActionListener(e -> handler());
        cancelButton.addActionListener(e -> this.dispose());

        setLayout(new GridLayout(3, 2));

        this.getContentPane().add(new JLabel("The current profit rate is " + trans.getCurrentProfitRate() + " per months"));
        this.getContentPane().add(blank);

        this.getContentPane().add(TimeLabel);
        this.getContentPane().add(TimeField);

        this.getContentPane().add(submitButton);
        this.getContentPane().add(cancelButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

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
