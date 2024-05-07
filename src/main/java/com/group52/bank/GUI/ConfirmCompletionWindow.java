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

public class ConfirmCompletionWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildTaskMenuWindow childTaskMenuWindow;
    private Child child;

    private JLabel taskTitle;
    private JTable myTaskTable;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JLabel taskIdLabel;
    private JTextField taskIdField;
    private JButton confirmButton;
    private JButton cancelButton;

    public ConfirmCompletionWindow(TaskSystem taskSystem, ChildTaskMenuWindow childTaskMenuWindow, Child child) {
        super("Confirm Task Completion");
        this.taskSystem = taskSystem;
        this.childTaskMenuWindow = childTaskMenuWindow;
        this.child = child;

        // Create Swing components and arrange them using a layout manager
        taskTitle = new JLabel("Your Tasks:");
        myTaskTable = getMyTask();
        scrollPane = new JScrollPane(myTaskTable);
        titleLabel = new JLabel("Confirm Task Completion");
        taskIdLabel = new JLabel("Task ID:");
        taskIdField = new JTextField(20);
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        confirmButton.addActionListener(e -> handleConfirmCompletion());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 2));

        // Add Swing components to the frame
        add(taskTitle);
        add(scrollPane);
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
        if (taskSystem.changeTaskState(taskId, "ChildComplete", child)) {
            taskSystem.saveTaskHistory();
            JOptionPane.showMessageDialog(this, "Task confirmed complete successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change task state. Task ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTable getMyTask(){
        List<Task> myTask = taskSystem.getChildsTask(child);
        if (myTask.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No task found."}}, new String[]{"Message"}));
        }

        // Create a 2D array of transaction data
        String[][] taskData = new String[myTask.size()][];

        for (int i = 0; i < myTask.size(); i++) {
            Task task = myTask.get(i);
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
