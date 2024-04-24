package com.group52.bank.GUI;

import com.group52.bank.model.Task;
import com.group52.bank.model.Transaction;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewTaskHistoryWindow extends JFrame {

    private TaskSystem taskSystem;

    private JLabel titleLabel;
    private JTextArea historyTextArea;
    private JButton closeButton;

    public ViewTaskHistoryWindow(TaskSystem taskSystem) {
        super("Task History");
        this.taskSystem = taskSystem;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Task History");
        historyTextArea = new JTextArea(20, 40);
        historyTextArea.setEditable(false);
        closeButton = new JButton("Close");

        // Add action listener for close button
        closeButton.addActionListener(e -> this.dispose()); // Close window on click

        // Populate task history text area
        populateHistoryTextArea();

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Add Swing components to the frame
        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(historyTextArea), BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void populateHistoryTextArea() {
        List<Task> taskHistory = taskSystem.getTaskHistory();
        StringBuilder historyText = new StringBuilder();
        for (Task task : taskHistory) {
            historyText.append(task.getDetails()).append("\n");
        }
        historyTextArea.setText(historyText.toString());
    }
}
