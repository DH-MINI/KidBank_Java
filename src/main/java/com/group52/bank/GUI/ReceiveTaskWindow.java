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
        taskListTitel = new JLabel("Unreceived Task List",JLabel.CENTER);
        titleLabel = new JLabel("Receive Task",JLabel.CENTER);
        taskIdLabel = new JLabel("Task ID:",JLabel.CENTER);
        taskIdField = new JTextField(20);
        receiveButton = new JButton("Receive");
        cancelButton = new JButton("Cancel");

        // Add action listeners for buttons
        receiveButton.addActionListener(e -> handleReceiveTask());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Add Swing components to the frame
        unrecTasksTable = createUnrecTaskTable();
        scrollPane = new JScrollPane(unrecTasksTable);



        // Create a panel with GridLayout to arrange components
        JPanel jp01 = new JPanel();
        JPanel jp02 = new JPanel();
        jp01.setBackground(Color.PINK);
        jp02.setBackground(Color.PINK);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setPreferredSize(new Dimension(400, 40));
        panel1.add(taskListTitel,BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setPreferredSize(new Dimension(400, 100));
        panel2.add(scrollPane,BorderLayout.CENTER);


        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(320, 90));
        panel3.setLayout(new GridLayout(3, 2));
        panel3.setBackground(Color.cyan);
        panel3.add(taskIdLabel);
        panel3.add(taskIdField);
        panel3.add(jp01);
        panel3.add(jp02);
        panel3.add(receiveButton);
        panel3.add(cancelButton);

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(400, 180));
        panel4.setLayout(new GridBagLayout());
        panel4.add(panel3,new GridBagConstraints());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(panel2,BorderLayout.CENTER);
        panel.add(panel4,BorderLayout.SOUTH);


        // Add panel to the frame

        JLabel labImageBJ = new JLabel(new ImageIcon("src/main/resources/Image/bg01.png"),JLabel.CENTER);
        this.setLayout(new BorderLayout());
        this.add(labImageBJ,BorderLayout.WEST);
        this.add(panel);


        // Set frame properties
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
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
