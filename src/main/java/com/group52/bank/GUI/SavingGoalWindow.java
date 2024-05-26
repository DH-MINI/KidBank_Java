package com.group52.bank.GUI;

import com.group52.bank.GUI.setting.CreateNewFont;
import com.group52.bank.GUI.setting.SetImageSize;
import com.group52.bank.model.Child;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class represents the saving goal window in the banking application.
 */
public class SavingGoalWindow extends JFrame {

    private Child child;
    private TransactionSystem transSystem;

    private JLabel balanceLabel;
    private JLabel savingGoalLabel;
    private JProgressBar progressBar;
    private JButton setSavingGoalButton;
    private JButton exitButton;
    private JLabel progressLabel;
    private JPanel progressBarPanel;
    private JLabel textLabel;
    /**
     * Constructs a new SavingGoalWindow with the given child and transaction system.
     *
     * @param child the child user
     * @param transSystem the transaction system
     */
    public SavingGoalWindow(Child child, TransactionSystem transSystem) throws IOException, FontFormatException {
        super("Saving Goal Management");
        this.child = child;
        this.transSystem = transSystem;

        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Balance Label
        balanceLabel = new JLabel("    Balance:     " + child.getBalance());
        balanceLabel.setFont(new CreateNewFont("PoetsenOne", 30f).getFont());
        balanceLabel.setForeground(new Color(123, 211, 71));

        // Saving Goal Label
        savingGoalLabel = new JLabel("    Saving Goal:     " + child.getSavingGoal());
        savingGoalLabel.setFont(new CreateNewFont("PoetsenOne", 30f).getFont());
        savingGoalLabel.setForeground(new Color(123, 211, 71));

        // Progress Bar
        progressBarPanel = new JPanel();
        progressBarPanel.setOpaque(false);
        progressBarPanel.setLayout(new GridLayout(1, 2, 100, 100));
        progressLabel = new JLabel("    Current Progress: ");
        progressLabel.setFont(new CreateNewFont("PoetsenOne", 30f).getFont());
        progressLabel.setForeground(new Color(123, 211, 71));
        progressBarPanel.add(progressLabel);
        progressBar = new JProgressBar(0, (int) child.getSavingGoal());
        progressBar.setValue((int) child.getBalance());
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(400, 50));
        progressBar.setForeground(new Color(231, 192, 19, 255));
        progressBar.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel barPanel = new JPanel();
        barPanel.setOpaque(false);
        barPanel.setLayout(new GridBagLayout());
        GridBagConstraints LabelConstraints = new GridBagConstraints();
        LabelConstraints.insets = new Insets(10, 0, 10, 0);
        barPanel.add(progressBar, LabelConstraints);
        progressBarPanel.add(barPanel);

        //TextLabel
        textLabel = createText(progressBar.getPercentComplete());
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.insets = new Insets(50, 0, 50, 0);
        textPanel.add(textLabel, textConstraints);


        // Set Saving Button
        setSavingGoalButton = createColoredButton("Set Saving Goal");
        setSavingGoalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter new saving goal:");
                try {
                    double newSavingGoal = Double.parseDouble(input);

                    System.out.println("New Saving Goal is " + newSavingGoal);

                    int flag = child.setSavingGoal(newSavingGoal);
                    if (flag == 0) {
                        transSystem.updateChildSavingGoal(child.getUsername(), newSavingGoal);
                        savingGoalLabel.setText("Saving Goal: " + child.getSavingGoal());
                        progressBar.setValue((int) child.getBalance());
                        progressBar.setMaximum((int) child.getSavingGoal());
                        progressBar.setStringPainted(true);
                    }
                    else if(flag == 1){
                        JOptionPane.showMessageDialog(null, "Invalid input! Haven't reach last saving goal.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(flag == 2){
                        JOptionPane.showMessageDialog(null, "Invalid input! You have reached that goal.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });


        // Set Exit Button
        exitButton = createColoredButton("Exit"); // 初始化退出按钮
        // Add action listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭窗口
            }
        });


        // Set layout
        setLayout(new GridLayout(5, 1, 50, 50)); // 使用 BorderLayout 布局管理器

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 50, 100)); // 一行两列
        buttonPanel.add(setSavingGoalButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        add(balanceLabel);
        add(savingGoalLabel);
        add(progressBarPanel);
        add(textPanel);
        add(buttonPanel);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600); // 设置初始窗口大小
        setLocationRelativeTo(null);
        setVisible(true);
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

    private JLabel createText(double percent) throws IOException, FontFormatException {
        textLabel = new JLabel();
        textLabel.setFont(new CreateNewFont("Caveat", 50f).getFont());
        textLabel.setForeground(new Color(227, 0, 0));
        if(percent == 1){
            textLabel.setText("Goal achieved! Congratulations!  ");
        }
        else if (percent > 0.7){
            textLabel.setText("Almost there! Keep going!  ");
        }
        else if (percent > 0.3){
            textLabel.setText("Great, keep it up!  ");
        }
        else{
            textLabel.setText("Come on, finish the goal!  ");
        }
        return textLabel;
    }
}
