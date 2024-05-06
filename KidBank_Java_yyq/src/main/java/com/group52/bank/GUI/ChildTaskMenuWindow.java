package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChildTaskMenuWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildMenuWindow childMenuWindow;

    private JLabel titleLabel;
    private JButton viewTasksButton;
    private JButton receiveTaskButton;
    private JButton confirmCompletionButton;
    private JButton backButton;
    private Child child;

    public ChildTaskMenuWindow(TaskSystem taskSystem, ChildMenuWindow childMenuWindow, Child child) {
        super("Task Management");
        this.taskSystem = taskSystem;
        this.childMenuWindow = childMenuWindow;
        this.child = child;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Task Management");
        viewTasksButton = new JButton("View Tasks");
        receiveTaskButton = new JButton("Receive Task");
        confirmCompletionButton = new JButton("Confirm Task Completion");
        backButton = new JButton("Back");

        // Add action listeners for buttons
        viewTasksButton.addActionListener(e -> handleViewTasks());
        receiveTaskButton.addActionListener(e -> handleReceiveTask());
        confirmCompletionButton.addActionListener(e -> handleConfirmCompletion());
        backButton.addActionListener(e -> this.dispose()); // Close window on back

        // Set layout manager for the frame
        setLayout(new GridLayout(5, 1));

        // Add Swing components to the frame
        add(titleLabel);
        add(viewTasksButton);
        add(receiveTaskButton);
        add(confirmCompletionButton);
        add(backButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleViewTasks() {
        // Open a separate window for viewing task history
        new ViewTaskHistoryWindow(taskSystem).setVisible(true);
    }


    private void handleReceiveTask() {
        // Open a separate window for receiving tasks
        new ReceiveTaskWindow(taskSystem, this, this.child).setVisible(true);
    }

    private void handleConfirmCompletion() {
        // Open a separate window for confirming task completion
        new ConfirmCompletionWindow(taskSystem, this).setVisible(true);
    }
}
