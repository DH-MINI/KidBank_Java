package com.group52.bank.GUI;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskChangeStateWindow extends JFrame {

    private TaskSystem taskSystem;
    private TaskMenuWindow taskMenuWindow;

    private JLabel titleLabel;
    private JLabel taskIdLabel;
    private JTextField taskIdField;
    private JLabel newStateLabel;
    private JComboBox<String> newStateComboBox;
    private JButton submitButton;
    private JButton cancelButton;

    public TaskChangeStateWindow(TaskSystem taskSystem, TaskMenuWindow taskMenuWindow) {
        super("Change Task State");
        this.taskSystem = taskSystem;
        this.taskMenuWindow = taskMenuWindow;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Change Task State");
        taskIdLabel = new JLabel("Task ID:");
        taskIdField = new JTextField(15);
        newStateLabel = new JLabel("New State:");
        newStateComboBox = new JComboBox<>(new String[]{"Complete", "Delete"});
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        submitButton.addActionListener(e -> handleChangeState());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 2));

        // Add Swing components to the frame
        add(titleLabel);
        add(new JLabel()); // Placeholder for empty cell
        add(taskIdLabel);
        add(taskIdField);
        add(newStateLabel);
        add(newStateComboBox);
        add(submitButton);
        add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleChangeState() {
        String taskId = taskIdField.getText();
        String newState = (String) newStateComboBox.getSelectedItem();

        if (taskSystem.changeTaskState(taskId, newState)) {
            JOptionPane.showMessageDialog(this, "Task state changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
//            taskMenuWindow.refreshTaskList(); // Optional: Update task list in task menu window
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change task state. Task ID not found or invalid state.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
