package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.model.Task;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReceiveTaskWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildTaskMenuWindow childTaskMenuWindow;

    private JLabel taskListTitel;
    private JLabel titleLabel;
    private JLabel taskIdLabel;
    private JTextField taskIdField;
    private JButton receiveButton;
    private JButton cancelButton;
    private Child child;

    private JTable unrecTasksTable;
    private JScrollPane scrollPane;

    public ReceiveTaskWindow(TaskSystem taskSystem, ChildTaskMenuWindow childTaskMenuWindow, Child child) {
        super("Receive Task");
        this.taskSystem = taskSystem;
        this.childTaskMenuWindow = childTaskMenuWindow;
        this.child = child;

        // Create Swing components and arrange them using a layout manager
        taskListTitel = new JLabel("Unreceived Task List");
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
        add(taskListTitel);
        unrecTasksTable = createUnrecTaskTable();
        scrollPane = new JScrollPane(unrecTasksTable);
//        scrollPane.setSize(new Dimension(1, 1));
        add(scrollPane);
//        add(titleLabel);
//        add(new JLabel()); // Empty label for layout spacing
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

    private JTable createUnrecTaskTable() {
        List<Task> tasks = taskSystem.getUnreceivedTasks();
        if (tasks.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No unreceived task found."}}, new String[]{"Message"}));
        }

        // Create a 2D array of transaction data
        String[][] taskData = new String[tasks.size()][];
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            taskData[i] = new String[]{task.getTaskId(), task.getDetails(), task.getReceivedBy(), "" + task.getReward(), task.getDeadline().toString(), task.getState()};
        }

        // Create column names for the table
        String[] columnNames = {"Task ID", "Description", "Received by", "Rewards", "Deadline", "State"};

        // Create a JTable model and set the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(taskData, columnNames);
        JTable table = new JTable(tableModel);

        // Optional: Adjust table column widths
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBCOLUMNS);

        return table;
    }
}
