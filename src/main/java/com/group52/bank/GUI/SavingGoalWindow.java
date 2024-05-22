package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SavingGoalWindow extends JFrame {

    private Child child;
    private TransactionSystem transSystem;

    private JLabel balanceLabel;
    private JLabel savingGoalLabel;
    private JProgressBar progressBar;
    private JButton setSavingGoalButton;
    private JButton exitButton;

    public SavingGoalWindow(Child child, TransactionSystem transSystem) {
        super("Saving Goal Management");
        this.child = child;
        this.transSystem = transSystem;

        // Create components
        balanceLabel = new JLabel("Balance: " + child.getBalance());
        savingGoalLabel = new JLabel("Saving Goal: " + child.getSavingGoal());
        progressBar = new JProgressBar(0, (int) child.getSavingGoal());
        progressBar.setValue((int) child.getBalance());
        progressBar.setStringPainted(true);

        setSavingGoalButton = new JButton("Set Saving Goal");
        exitButton = new JButton("Exit"); // 初始化退出按钮

        // Add action listener to the set saving goal button
        setSavingGoalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter new saving goal:");
                try {
                    double newSavingGoal = Double.parseDouble(input);



                    if (child.setSavingGoal(newSavingGoal)) {
                        transSystem.updateChildSavingGoal(child.getUsername(), newSavingGoal);
                        savingGoalLabel.setText("Saving Goal: " + child.getSavingGoal());
                        progressBar.setValue((int) child.getBalance());
                        progressBar.setMaximum((int) child.getSavingGoal());
                        progressBar.setStringPainted(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Invalid input! Haven't reach last saving goal.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        // Add action listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭窗口
            }
        });

        // Set layout
        setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2)); // 一行两列
        buttonPanel.add(setSavingGoalButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        add(balanceLabel, BorderLayout.NORTH);
        add(savingGoalLabel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST); // 放置按钮面板在东边

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400); // 设置初始窗口大小
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
