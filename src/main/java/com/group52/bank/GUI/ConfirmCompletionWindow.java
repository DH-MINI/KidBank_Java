package com.group52.bank.GUI;


import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmCompletionWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildTaskMenuWindow childTaskMenuWindow;

    private JLabel titleLabel;
    private JLabel taskIdLabel;
    private JTextField taskIdField;
    private JButton confirmButton;
    private JButton cancelButton;

    public ConfirmCompletionWindow(TaskSystem taskSystem, ChildTaskMenuWindow childTaskMenuWindow) {
        super("Confirm Task Completion");
        this.taskSystem = taskSystem;
        this.childTaskMenuWindow = childTaskMenuWindow;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Confirm Task Completion");
        taskIdLabel = new JLabel("Task ID:");
        taskIdField = new JTextField(20);
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        confirmButton.addActionListener(e -> handleConfirmCompletion());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(3, 2));

        // Add Swing components to the frame
        add(titleLabel);
        add(new JLabel()); // Empty label for layout spacing
        add(taskIdLabel);
        add(taskIdField);
        add(confirmButton);
        add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleConfirmCompletion() {
        String taskId = taskIdField.getText();
        if (taskSystem.changeTaskState(taskId, "ChildComplete")) {
            taskSystem.saveTaskHistory();
            JOptionPane.showMessageDialog(this, "Task confirmed complete successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change task state. Task ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
