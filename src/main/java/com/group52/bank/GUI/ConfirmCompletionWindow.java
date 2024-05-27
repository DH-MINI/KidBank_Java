package com.group52.bank.GUI;


import com.group52.bank.model.Child;
import com.group52.bank.model.Task;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.GUI.setting.CreateNewFont;
import com.group52.bank.GUI.setting.SetImageSize;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
/**
 * This class represents the confirm completion window in the banking application.
 */
public class ConfirmCompletionWindow extends JFrame {

    private TaskSystem taskSystem;
    private ChildTaskMenuWindow childTaskMenuWindow;
    private Child child;

    private JLabel taskTitle;
    private JTable myTaskTable;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JTextField taskIdField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel confirmPanel;
    private JPanel cancelPanel;
    private JPanel textPanel;

    private ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
    private ImageIcon LabelImage = new ImageIcon("src/main/resources/Image/Label1.png");
    /**
     * Constructs a new ConfirmCompletionWindow with the given parameters.
     *
     * @param taskSystem the task system
     * @param childTaskMenuWindow the child task menu window
     * @param child the child user
     * @throws IOException if there is an error reading the font file
     * @throws FontFormatException if the font file format is not supported
     */
    public ConfirmCompletionWindow(TaskSystem taskSystem, ChildTaskMenuWindow childTaskMenuWindow, Child child) throws IOException, FontFormatException {
        super("Confirm Task Completion");
        this.taskSystem = taskSystem;
        this.childTaskMenuWindow = childTaskMenuWindow;
        this.child = child;

        //Frame Settings
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        this.setLayout(new BorderLayout(50, 30));

        // Set the TaskTable
        taskTitle = new JLabel("Your Tasks:");
        myTaskTable = getMyTask();
        scrollPane = new JScrollPane(myTaskTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 250));


        // Set the TitleLabel
        titleLabel = new JLabel();
        titleLabel.setLayout(new BorderLayout());
        titleLabel.setIcon(new SetImageSize(LabelImage, 0.25).getScaledImage());
        JLabel titleTextLabel = new JLabel(" Confirm Your Task Completion!");
        titleTextLabel.setFont(new CreateNewFont("PoetsenOne", 28f).getFont());
        titleLabel.add(titleTextLabel, BorderLayout.CENTER);
        JPanel titleLabelPanel = new JPanel();
        titleLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints LabelConstraints = new GridBagConstraints();
        LabelConstraints.insets = new Insets(50, 100, 50, 50);
        titleLabelPanel.add(titleLabel, LabelConstraints);
        titleLabelPanel.setOpaque(false);


        //Set the text field
        textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false);
        taskIdField = new JTextField();
        taskIdField.setPreferredSize(new Dimension(600,100));
        taskIdField.setFont(new CreateNewFont("Caveat", 30f).getFont());
        taskIdField.setToolTipText("Enter  task  ID  and  click  'Confirm'");
        taskIdField.setText("Enter  task  ID  and  click  'Confirm'");
        taskIdField.setForeground(new Color(204, 204, 204));
        //Add a Tip to the TextField
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
        constraints.insets = new Insets(0, 10, 0, 0);
        textPanel.add(taskIdField, constraints);



        //Set buttons
        confirmPanel = new JPanel();
        cancelPanel = new JPanel();
        confirmPanel.setOpaque(false);
        cancelPanel.setOpaque(false);
        confirmButton = createColoredButton("Confirm");
        cancelButton = createColoredButton("Cancel");
        confirmButton.setPreferredSize(new Dimension(200, 100));
        cancelButton.setPreferredSize(new Dimension(200, 100));
        confirmPanel.add(confirmButton);
        cancelPanel.add(cancelButton);
        // Add action listeners for buttons
        confirmButton.addActionListener(e -> handleConfirmCompletion());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 50));
        buttonPanel.add(confirmPanel);
        buttonPanel.add(cancelPanel);


        // Add Swing components to the frame
        add(scrollPane, BorderLayout.NORTH);
        add(titleLabelPanel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }
    /**
     * Handles the confirm completion action.
     */
    private void handleConfirmCompletion() {
        String taskId = taskIdField.getText();
        if (taskSystem.changeTaskState(taskId, "ChildComplete", child)) {
            taskSystem.saveTaskHistory();
            JOptionPane.showMessageDialog(this, "Well done! Now please waiting for your parent checking your job!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change task state. Task ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Returns a table of the child's tasks.
     *
     * @return a table of the child's tasks
     */
    private JTable getMyTask(){
        List<Task> myTask = taskSystem.getChildsTask(child);
        if (myTask.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No task found."}}, new String[]{"Message"}));
        }

        // Create a 2D array of transaction data
        String[][] taskData = new String[myTask.size()][];

        for (int i = 0; i < myTask.size(); i++) {
            Task task = myTask.get(i);
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
        table.getColumnModel().getColumn(1).setPreferredWidth(500);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);

        return table;
    }
    /**
     * Creates a colored button with the given text.
     *
     * @param text the text for the button
     * @return the created button
     * @throws IOException if there is an error reading the font file
     * @throws FontFormatException if the font file format is not supported
     */
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
