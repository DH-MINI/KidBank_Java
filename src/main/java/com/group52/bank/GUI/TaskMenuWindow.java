package com.group52.bank.GUI;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class TaskMenuWindow extends JFrame {

        private TaskSystem taskSystem;
        private ParentMenuWindow parentMenuWindow;

        private JLabel titleLabel;
        private JButton publishTaskButton;
        private JButton manageTasksButton;
        private JButton viewTaskHistoryButton;
        private JButton backButton;

        public TaskMenuWindow(TaskSystem taskSystem, ParentMenuWindow parentMenuWindow) {
            super("Task Management Submenu");
            this.taskSystem = taskSystem;
            this.parentMenuWindow = parentMenuWindow;

            // Create Swing components and arrange them using a layout manager
            titleLabel = new JLabel("Task Management Submenu");
            publishTaskButton = new JButton("Publish Task");
            manageTasksButton = new JButton("Change Task State");
            viewTaskHistoryButton = new JButton("View Task History");
            backButton = new JButton("Back to Parent Menu");

            // Add action listeners for buttons
            publishTaskButton.addActionListener(e -> handlePublishTask());
            manageTasksButton.addActionListener(e -> handleChangeTaskState()); // Optional: Open separate window for managing tasks
            viewTaskHistoryButton.addActionListener(e -> handleViewTaskHistory()); // Call existing method (optional)
            backButton.addActionListener(e -> this.dispose()); // Close window on back

            // Set layout manager for the frame
            setLayout(new GridLayout(5, 1));

            // Add Swing components to the frame
            add(titleLabel);
            add(publishTaskButton);
            add(manageTasksButton);
            add(viewTaskHistoryButton);
            add(backButton);

            // Set frame properties
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setLocationRelativeTo(null); // Center the window on screen
            setVisible(true);
        }

        private void handlePublishTask() {
            // Open a separate window for task creation
            new PublishTaskWindow(taskSystem, this).setVisible(true);
        }

        private void handleChangeTaskState() {
            // Open a separate window for changing task state
            new TaskChangeStateWindow(taskSystem, this).setVisible(true);
        }
        private void handleViewTaskHistory() {
            // Open a separate window for viewing task history
            new ViewTaskHistoryWindow(taskSystem).setVisible(true);
        }

    }
