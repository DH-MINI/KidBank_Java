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
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.BLUE);

        currentRate = new JLabel("The current rate is " + trans.getCurrentProfitRate());
        currentRate.setFont(new Font("Arial", Font.PLAIN, 14));

        rateField = new JTextField(10);
        rateField.setFont(new Font("Arial", Font.PLAIN, 14));

        apply = new JButton("Apply");
        apply.setFont(new Font("Arial", Font.BOLD, 14));
        apply.setForeground(Color.GREEN);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.BOLD, 14));
        cancel.setForeground(Color.RED);

        apply.addActionListener(e -> handler());
        cancel.addActionListener(e -> this.dispose());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(currentRate, gbc);

        gbc.gridy = 2;
        panel.add(rateField, gbc);

        gbc.gridy = 3;
        panel.add(apply, gbc);

        gbc.gridy = 4;
        panel.add(cancel, gbc);

        this.getContentPane().add(panel);

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
