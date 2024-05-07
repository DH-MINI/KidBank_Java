package com.group52.bank.GUI;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;

public class SetProfitRateWindow extends JFrame {
    private TransactionSystem trans;
    private JLabel currentRate, title;
    private JTextField rateField;
    private JButton apply, cancel;

    public SetProfitRateWindow(TransactionSystem trans){
        super("Set Profit Rate of Term Deposit");

        this.trans = trans;

        title = new JLabel("Please enter the rate you want in the text field");
        currentRate = new JLabel("The current rate is " + trans.getCurrentProfitRate());
        rateField = new JTextField(10);
        apply = new JButton("Apply");
        cancel = new JButton("Cancel");

        apply.addActionListener(e -> handler());
        cancel.addActionListener(e -> this.dispose());

        this.getContentPane().setLayout(new GridLayout(5,1));

        this.getContentPane().add(title);
        this.getContentPane().add(currentRate);
        this.getContentPane().add(rateField);
        this.getContentPane().add(apply);
        this.getContentPane().add(cancel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    public boolean handler(){
        double newRate;
        try {
            newRate = Double.parseDouble(rateField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(trans.setProfitRate(newRate)){
            JOptionPane.showMessageDialog(this, "New rate is set successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            return true;
        }
        else {
            JOptionPane.showMessageDialog(this, "New rate setting failed.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return false;
        }
    }
}
