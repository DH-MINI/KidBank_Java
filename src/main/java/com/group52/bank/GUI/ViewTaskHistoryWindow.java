package com.group52.bank.GUI;

import com.group52.bank.model.Task;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * This class represents the window for viewing the task history in the banking application.
 */
public class ViewTaskHistoryWindow extends JFrame {

    private TaskSystem taskSystem;

    private JLabel titleLabel;
    private JTable historyTable;
    private JButton closeButton;
    /**
     * Constructs a new ViewTaskHistoryWindow with the given task system.
     *
     * @param taskSystem the task system
     */
    public ViewTaskHistoryWindow(TaskSystem taskSystem) {
        super("Task History");
        this.taskSystem = taskSystem;

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("Task History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Change font
        titleLabel.setForeground(Color.YELLOW); // Change color

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Change font
        closeButton.setForeground(Color.RED); // Change text color

        // Add action listener for close button
        closeButton.addActionListener(e -> this.dispose()); // Close window on click

        // Populate task history table
        populateHistoryTable();

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Add Swing components to the frame
        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(historyTable), BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(1000,500);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);

        // Change window background color
        getContentPane().setBackground(Color.LIGHT_GRAY);
    }
    /**
     * Populates the task history table with data.
     */
    private void populateHistoryTable() {
        List<Task> taskHistory = taskSystem.getTaskHistory();

        // Create table data
        String[][] data = new String[taskHistory.size()][6];
        for (int i = 0; i < taskHistory.size(); i++) {
            Task task = taskHistory.get(i);
            data[i][0] = task.getTaskId();
            data[i][1] = task.getDescription();
            data[i][2] = String.valueOf(task.getReward());
            data[i][3] = task.getDeadline().toString();
            data[i][4] = task.getState();
            data[i][5] = task.getReceivedBy();
        }

        // Create table headers
        String[] headers = {"Task ID", "Description", "Reward", "Deadline", "State", "Received By"};

        // Create table
        historyTable = new JTable(data, headers);
        historyTable.setAutoCreateRowSorter(true); // Enable sorting
    }
}