package com.group52.bank.GUI;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PublishTaskWindow extends JFrame {

    private TaskSystem taskSystem;
    private TaskMenuWindow taskMenuWindow;

    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JLabel rewardLabel;
    private JTextField rewardField;
    private JLabel deadlineLabel;
    private JFormattedTextField deadlineField; // Use JFormattedTextField for date input
    private JButton publishButton;
    private JButton cancelButton;

    public PublishTaskWindow(TaskSystem taskSystem, TaskMenuWindow taskMenuWindow) {
        super("Publish Task");
        this.taskSystem = taskSystem;
        this.taskMenuWindow = taskMenuWindow;

        // Set layout manager for the frame
        setLayout(new GridLayout(5, 2));

        // Create Swing components

        descriptionLabel = new JLabel("Task Description:");
        add(descriptionLabel);
        descriptionField = new JTextField(20);
        add(descriptionField);

        rewardLabel = new JLabel("Reward ($):");
        add(rewardLabel);
        rewardField = new JTextField(10);
        add(rewardField);

        deadlineLabel = new JLabel("Deadline (yyyy-MM-dd):");
        add(deadlineLabel);
        deadlineField = new JFormattedTextField(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Formatted date input
        add(deadlineField);

        publishButton = new JButton("Publish");
        add(publishButton);
        publishButton.addActionListener(e -> handlePublish());

        cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handlePublish() {
        String description = descriptionField.getText();
        double reward;
        try {
            reward = Double.parseDouble(rewardField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid reward amount. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate deadline;
        try {
            deadline = LocalDate.parse(deadlineField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter date in yyyy-MM-dd format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        taskSystem.pushTask(description, reward, deadline);
        JOptionPane.showMessageDialog(this, "Task published successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // Close window on success
        // taskMenuWindow.refreshTaskList(); // Optional: Update task list in parent menu (implementation details omitted)
    }
}
