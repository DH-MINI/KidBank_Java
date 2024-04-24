package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceiveTaskWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildTaskMenuWindow childTaskMenuWindow;

    private JLabel titleLabel;
    private JLabel taskIdLabel;
    private JTextField taskIdField;
    private JButton receiveButton;
    private JButton cancelButton;
    private Child child;

    public ReceiveTaskWindow(TaskSystem taskSystem, ChildTaskMenuWindow childTaskMenuWindow, Child child) {
        super("Receive Task");
        this.taskSystem = taskSystem;
        this.childTaskMenuWindow = childTaskMenuWindow;
        this.child = child;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Receive Task");
        taskIdLabel = new JLabel("Task ID:");
        taskIdField = new JTextField(20);
        receiveButton = new JButton("Receive");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        receiveButton.addActionListener(e -> handleReceiveTask());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(3, 2));

        // Add Swing components to the frame
        add(titleLabel);
        add(new JLabel()); // Empty label for layout spacing
        add(taskIdLabel);
        add(taskIdField);
        add(receiveButton);
        add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleReceiveTask() {
        String receiveTaskId = taskIdField.getText();
        if (taskSystem.receiveTask(receiveTaskId, child.getUsername())) {
            taskSystem.saveTaskHistory();
            JOptionPane.showMessageDialog(this, "Task received successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
        } else {
            JOptionPane.showMessageDialog(this, "Failed to receive task. Task ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
