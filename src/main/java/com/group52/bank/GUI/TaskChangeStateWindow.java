package com.group52.bank.GUI;

import com.group52.bank.model.Task;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskChangeStateWindow extends JFrame {

    private TaskSystem taskSystem;
    private TaskMenuWindow taskMenuWindow;

    private JLabel taskTitle;
    private JTable publishedTasks;
    private JScrollPane scrollPane;
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
        taskTitle = new JLabel("Task List:");
        publishedTasks = getTasksTable();
        scrollPane = new JScrollPane(publishedTasks);
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
        setLayout(new GridLayout(5, 2));

        // Add Swing components to the frame
        add(taskTitle);
        add(scrollPane);
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

    private JTable getTasksTable(){
        List<Task> tasks = taskSystem.getTaskHistory();
        if (tasks.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No published tasks found."}}, new String[]{"Message"}));
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
