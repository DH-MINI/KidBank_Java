package com.group52.bank.GUI;

import com.group52.bank.model.Parent;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TaskMenuWindow extends JFrame {

        private TaskSystem taskSystem;
        private ParentMenuWindow parentMenuWindow;

        private Parent parent;
        private JLabel titleLabel;
        private JButton publishTaskButton;
        private JButton manageTasksButton;
        private JButton viewTaskHistoryButton;
        private JButton backButton;

        public TaskMenuWindow(TaskSystem taskSystem, ParentMenuWindow parentMenuWindow, Parent parent) {
            super("Task Management Submenu");
            this.taskSystem = taskSystem;
            this.parentMenuWindow = parentMenuWindow;
            this.parent = parent;

            // Load background image
            ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
            JLabel backgroundLabel = new JLabel(backgroundImage);
            setContentPane(backgroundLabel);

            // Ensure layout manager of JLabel is null to freely position components
            backgroundLabel.setLayout(null);

            setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();

            // Create a JPanel to hold buttons
            JPanel buttonContainer = new JPanel();
            buttonContainer.setOpaque(false); // Set the background to be transparent
            buttonContainer.setLayout(new GridLayout(4, 1, 0, 30)); // Use GridLayout for vertical arrangement with gaps
            backgroundLabel.add(buttonContainer); //Add the container to the background label

            // Create Swing components and arrange them using a layout manager
            titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                    "<span style='font-size: 60px;'>Task Management Submenu</span>" +
                    "</div></html>", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

            publishTaskButton = createColoredButton("Publish Task");
            manageTasksButton = createColoredButton("Change Task State");
            viewTaskHistoryButton = createColoredButton("View Task History");
            backButton = createColoredButton("Back to Parent Menu");

            buttonContainer.add(publishTaskButton);
            buttonContainer.add(manageTasksButton);
            buttonContainer.add(viewTaskHistoryButton);
            buttonContainer.add(backButton);
//
//            // Create Swing components and arrange them using a layout manager
//            titleLabel = new JLabel("Task Management Submenu");
//            publishTaskButton = new JButton("Publish Task");
//            manageTasksButton = new JButton("Change Task State");
//            viewTaskHistoryButton = new JButton("View Task History");
//            backButton = new JButton("Back to Parent Menu");

            // Add action listeners for buttons
            publishTaskButton.addActionListener(e -> handlePublishTask());
            manageTasksButton.addActionListener(e -> {
                try {
                    handleChangeTaskState();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
            }); // Optional: Open separate window for managing tasks
            viewTaskHistoryButton.addActionListener(e -> handleViewTaskHistory()); // Call existing method (optional)
            backButton.addActionListener(e -> this.dispose()); // Close window on back



            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(buttonPanel, BorderLayout.CENTER);

            // Add image to the left side
            JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/Image/twokids.png"));
            imageLabel.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

            // Add components to frame
            add(titleLabel, BorderLayout.NORTH);
            add(imageLabel, BorderLayout.WEST);
            add(rightPanel, BorderLayout.EAST);

            // Set frame properties
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(1290, 800);
            setLocationRelativeTo(null);
            setVisible(true);

//            // Set layout manager for the frame
//            setLayout(new GridLayout(5, 1));
//
//            // Add Swing components to the frame
//            add(titleLabel);
//            add(publishTaskButton);
//            add(manageTasksButton);
//            add(viewTaskHistoryButton);
//            add(backButton);
//
//            // Set frame properties
//            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            pack();
//            setLocationRelativeTo(null); // Center the window on screen
//            setVisible(true);
        }

        private void handlePublishTask() {
            // Open a separate window for task creation
            new PublishTaskWindow(taskSystem, this).setVisible(true);
        }

        private void handleChangeTaskState() throws IOException, FontFormatException {
            // Open a separate window for changing task state
            new TaskChangeStateWindow(taskSystem, this, parent).setVisible(true);
        }
        private void handleViewTaskHistory() {
            // Open a separate window for viewing task history
            new ViewTaskHistoryWindow(taskSystem).setVisible(true);
        }

        private JButton createColoredButton(String text) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24)); // Decrease font size
            button.setBackground(Color.ORANGE);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(200, 20)); // Adjust preferred size for the button
            button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
            return button;
        }

    }
