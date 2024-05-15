package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ChildrensBankingApp extends JFrame {

    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";
    private static final String TRANSACTION_HISTORY_CSV = "src/main/resources/datacsv/transactionHistory.csv";
    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";
    private static final String profitRateCSV = "src/main/resources/datacsv/profitRate.csv";

    AuthenticationSystem authSystem;
    TransactionSystem transSystem;
    TaskSystem taskSystem;
    LoginWindow loginWindow;

    public ChildrensBankingApp() {
        super("Children's Banking App");

        // Initialize authentication, transaction, and task systems (use your existing code)
        authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
        transSystem = new TransactionSystem(TRANSACTION_HISTORY_CSV, CHILD_CSV, profitRateCSV);
        taskSystem = new TaskSystem(TASK_CSV, CHILD_CSV);

        // Welcome text
        JLabel welcomeText = new JLabel("Welcome to Children's Bank!");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 30));

        // Explore button
        JButton exploreButton = new JButton("Explore Now");
        exploreButton.setBackground(new Color(128, 0, 128)); // Purple color (RGB: 128, 0, 128)
//        exploreButton.setBackground(new Color(255,0,0));
        exploreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWindow = new LoginWindow(ChildrensBankingApp.this); // Use ChildrensBankingApp.this to refer to the outer class instance
                loginWindow.setVisible(true);
                dispose(); // Close the welcome window
            }
        });

        // Content panel layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make the content panel transparent
        contentPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        contentPanel.add(welcomeText, BorderLayout.NORTH);
        contentPanel.add(exploreButton, BorderLayout.CENTER);

        // Right panel image
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/main.png")));
        Image image = imageIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        JLabel imageView = new JLabel(new ImageIcon(image));
        imageView.setBorder(new EmptyBorder(0, 150, 0, 0));

        // Main panel layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 179, 71)); // Orange color (RGB: 255, 179, 71)
        mainPanel.add(contentPanel, BorderLayout.WEST);
        mainPanel.add(imageView, BorderLayout.CENTER);

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 179, 71)); // Set the background color of the frame
        getContentPane().add(mainPanel);
        setSize(1200, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ChildrensBankingApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
