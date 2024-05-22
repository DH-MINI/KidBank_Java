package com.group52.bank.GUI;

import com.group52.bank.GUI.setting.CreateNewFont;
import com.group52.bank.GUI.setting.SetImageSize;
import com.group52.bank.model.Parent;
import com.group52.bank.model.Task;
import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

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
    private Parent parent;
    private ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
    private ImageIcon LabelImage = new ImageIcon("src/main/resources/Image/Label1.png");
    private JPanel centerPanel;

    public TaskChangeStateWindow(TaskSystem taskSystem, TaskMenuWindow taskMenuWindow, Parent parent) throws IOException, FontFormatException {
        super("Change Task State");
        this.taskSystem = taskSystem;
        this.taskMenuWindow = taskMenuWindow;
        this.parent = parent;

        // Frame Settings
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        this.setLayout(new BorderLayout(50, 30));

        // Set the TaskTable
        publishedTasks = getTasksTable();
        scrollPane = new JScrollPane(publishedTasks, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 250));
        add(scrollPane, BorderLayout.NORTH);

        // Add a new panel at the center
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2));
        centerPanel.setOpaque(false);

        // Set the TextLabel
        JPanel titleLabelPanel = new JPanel();
        JLabel textLabel = new JLabel();
        textLabel.setLayout(new BorderLayout());
        textLabel.setIcon(new SetImageSize(LabelImage, 0.3).getScaledImage());
        titleLabel = new JLabel("  Confirm Children's Tasks!");
        titleLabel.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        textLabel.add(titleLabel, BorderLayout.CENTER);
        titleLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints LabelConstraints = new GridBagConstraints();
        LabelConstraints.insets = new Insets(50, 100, 50, 50);
        titleLabelPanel.add(textLabel, LabelConstraints);
        titleLabelPanel.setOpaque(false);
        centerPanel.add(titleLabelPanel);

        // Set the text field
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false);
        taskIdField = new JTextField();
        taskIdField.setPreferredSize(new Dimension(600,100));
        taskIdField.setFont(new CreateNewFont("Caveat", 35f).getFont());
        taskIdField.setToolTipText("Enter  task  ID  and  click  'Confirm'");
        taskIdField.setText("Enter  task  ID  and  click  'Confirm'");
        taskIdField.setForeground(new Color(204, 204, 204));
        taskIdField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(taskIdField.getText().equals("Enter  task  ID  and  click  'Confirm'")){
                    taskIdField.setText("");
                    taskIdField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(taskIdField.getText().isEmpty()){
                    taskIdField.setText("Enter  task  ID  and  click  'Confirm'");
                    taskIdField.setForeground(new Color(204, 204, 204));
                }
            }
        });
        // Set constraints of textfield
        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.weightx = 0.0;
//        constraints.weighty = 0.0;
//        constraints.fill = GridBagConstraints.NONE;
//        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(50, 200, 50, 50);
        textPanel.add(taskIdField, constraints);
        centerPanel.add(textPanel);

        // Set StateLabel
        JLabel stateLabel = new JLabel();
        stateLabel.setLayout(new BorderLayout());
        stateLabel.setIcon(new SetImageSize(LabelImage, 0.3).getScaledImage());
        newStateLabel = new JLabel("       Select  the  New  State");
        newStateLabel.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        stateLabel.add(newStateLabel, BorderLayout.CENTER);
        JPanel stateLabelPanel = new JPanel();
        stateLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints stateLabelConstraints = new GridBagConstraints();
        stateLabelConstraints.insets = new Insets(50, 100, 50, 50);
        stateLabelPanel.add(stateLabel, stateLabelConstraints);
        stateLabelPanel.setOpaque(false);
        centerPanel.add(stateLabelPanel);

        // Set state combo box
        JPanel stateComboPanel = new JPanel();
        stateComboPanel.setLayout(new GridBagLayout());
        GridBagConstraints stateBoxConstraints = new GridBagConstraints();
        stateBoxConstraints.insets = new Insets(50, 200, 50, 50);
        newStateComboBox = new JComboBox<>(new String[]{"Complete", "Delete"});
        newStateComboBox.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        newStateComboBox.setPreferredSize(new Dimension(600, 100));
        stateComboPanel.add(newStateComboBox, stateBoxConstraints);
        stateComboPanel.setOpaque(false);
        centerPanel.add(stateComboPanel);

        //Add the CenterPanel
        add(centerPanel, BorderLayout.CENTER);

        //Set Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 50));
        JPanel confirmPanel = new JPanel();
        JPanel cancelPanel = new JPanel();
        confirmPanel.setOpaque(false);
        cancelPanel.setOpaque(false);
        submitButton = createColoredButton("Confirm");
        cancelButton = createColoredButton("Cancel");
        submitButton.setPreferredSize(new Dimension(200, 100));
        cancelButton.setPreferredSize(new Dimension(200, 100));
        confirmPanel.add(submitButton);
        cancelPanel.add(cancelButton);

        // Add action listeners for buttons
        submitButton.addActionListener(e -> handleChangeState());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel
        buttonPanel.add(confirmPanel);
        buttonPanel.add(cancelPanel);

        add(buttonPanel, BorderLayout.SOUTH);


        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleChangeState() {
        String taskId = taskIdField.getText();
        String newState = (String) newStateComboBox.getSelectedItem();
        System.out.println(taskId);
        System.out.println(newState);
        if (taskSystem.changeTaskState(taskId, newState, parent)) {
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
            taskData[i] = new String[]{task.getTaskId(), task.getDescription(), task.getReceivedBy(), "" + task.getReward(), task.getDeadline().toString(), task.getState()};
        }

        // Create column names for the table
        String[] columnNames = {"Task ID", "Description", "Received by", "Rewards", "Deadline", "State"};

        // Create a JTable model and set the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(taskData, columnNames);
        JTable table = new JTable(tableModel);
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        // Optional: Adjust table column widths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(230);
        table.getColumnModel().getColumn(1).setPreferredWidth(600);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);

        return table;
    }
    private JButton createColoredButton(String text) throws IOException, FontFormatException {
        JButton button = new JButton(text);
        button.setFont(new CreateNewFont("PermanentMarker", 37f).getFont()); // Decrease font size
        button.setBackground(Color.ORANGE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 30)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }
}
