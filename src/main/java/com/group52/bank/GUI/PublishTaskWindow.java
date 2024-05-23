package com.group52.bank.GUI;

import com.group52.bank.task.TaskSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
/**
 * This class represents the window for publishing a task in the banking application.
 */
public class PublishTaskWindow extends JFrame {

    private TaskSystem taskSystem;
    private TaskMenuWindow taskMenuWindow;

    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JLabel rewardLabel;
    private JTextField rewardField;
    private JLabel deadlineLabel;
    private DateChooser dateChooser;  // 使用 DateChooser 组件
    private JButton publishButton;
    private JButton cancelButton;
    /**
     * Constructs a new PublishTaskWindow with the given task system and task menu window.
     *
     * @param taskSystem the task system
     * @param taskMenuWindow the task menu window
     */
    public PublishTaskWindow(TaskSystem taskSystem, TaskMenuWindow taskMenuWindow) {
        super("Publish Task");
        this.taskSystem = taskSystem;
        this.taskMenuWindow = taskMenuWindow;

        // 设置背景图片
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // 确保 JLabel 的布局管理器为 null 以便自由定位组件
        backgroundLabel.setLayout(null);

        // 设置布局管理器为 GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 创建并添加组件
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 60px;'>Publish Task</span></div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 30, 10); // 上移标题
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        descriptionLabel = new JLabel("Task Description:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(descriptionLabel, gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 30));
        add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rewardLabel = new JLabel("Reward ($):");
        rewardLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(rewardLabel, gbc);

        gbc.gridx = 1;
        rewardField = new JTextField(10);
        rewardField.setFont(new Font("Arial", Font.PLAIN, 30));
        add(rewardField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        deadlineLabel = new JLabel("Deadline:");
        deadlineLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(deadlineLabel, gbc);

        gbc.gridx = 1;
        dateChooser = new DateChooser();  // 使用 DateChooser 组件
        dateChooser.setPreferredSize(new Dimension(300, 30));
        add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // 使按钮占据两列宽度
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 使用GridLayout确保按钮大小一致
        buttonPanel.setOpaque(false); // 使按钮面板透明

        publishButton = new JButton("Publish");
        publishButton.setFont(new Font("Arial", Font.PLAIN, 30));
        publishButton.setBackground(new Color(173, 216, 230));
        buttonPanel.add(publishButton);
        publishButton.addActionListener(e -> handlePublish());

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 30));
        cancelButton.setBackground(new Color(255, 182, 193));
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose());

        add(buttonPanel, gbc);

        // 设置框架属性
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800); // 设置窗口大小与 ParentMenuWindow 一致
        setLocationRelativeTo(null); // 居中窗口
        setVisible(true);
    }
    /**
     * Handles the publishing of a task.
     */
    private void handlePublish() {
        String description = descriptionField.getText();
        double reward;
        try {
            reward = Double.parseDouble(rewardField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid reward amount. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date deadlineDate = dateChooser.getDate();
        if (deadlineDate == null) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate deadline = deadlineDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        taskSystem.pushTask(description, reward, deadline);
        JOptionPane.showMessageDialog(this, "Task published successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // 关闭窗口
        // taskMenuWindow.refreshTaskList(); // 可选：更新父菜单中的任务列表（省略实现细节）
    }
}
