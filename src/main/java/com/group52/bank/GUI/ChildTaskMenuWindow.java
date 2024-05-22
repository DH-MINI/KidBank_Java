package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
                "<span style='font-size: 60px;'>Task Management</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

        viewTasksButton = createColoredButton("View Tasks");
        receiveTaskButton = createColoredButton("Receive Task");
        confirmCompletionButton = createColoredButton("Confirm Task Completion");
        backButton = createColoredButton("Back");

        buttonContainer.add(viewTasksButton);
        buttonContainer.add(receiveTaskButton);
        buttonContainer.add(confirmCompletionButton);
        buttonContainer.add(backButton);

        // Setup event handlers for buttons
        setupEventHandlers();

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

    private void setupEventHandlers() {
        viewTasksButton.addActionListener(e -> handleViewTasks());
        receiveTaskButton.addActionListener(e -> handleReceiveTask());
        confirmCompletionButton.addActionListener(e -> {
            try {
                handleConfirmCompletion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.addActionListener(e -> this.dispose()); // Close window on back
    }

    private void handleViewTasks() {
        // Open a separate window for viewing task history
        new ViewTaskHistoryWindow(taskSystem).setVisible(true);
    }

    private void handleReceiveTask() {
        // Open a separate window for receiving tasks
        new ReceiveTaskWindow(taskSystem, this, this.child).setVisible(true);
    }

    private void handleConfirmCompletion() throws IOException, FontFormatException {
        // Open a separate window for confirming task completion
        new ConfirmCompletionWindow(taskSystem, this, this.child).setVisible(true);
    }
}
