package com.group52.bank.GUI;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;

import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TransactionMenuWindow extends JFrame {

    private TransactionSystem transSystem;
    private ParentMenuWindow parentMenuWindow;

    private JLabel titleLabel;
    private JButton viewHistoryButton;
    private JButton changeStateButton;
    private JButton backButton;

    public TransactionMenuWindow(TransactionSystem transSystem, ParentMenuWindow parentMenuWindow) {
        super("Transaction Management");
        this.transSystem = transSystem;
        this.parentMenuWindow = parentMenuWindow;

        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Ensure layout manager of JLabel is null to freely position components
        backgroundLabel.setLayout(null);

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        // Create a JPanel to hold buttons
        JPanel buttonContainer = new JPanel();
        buttonContainer.setOpaque(false); // Set the background to be transparent
        buttonContainer.setLayout(new GridLayout(3, 1, 0, 30)); // Use GridLayout for vertical arrangement with gaps
        backgroundLabel.add(buttonContainer); //Add the container to the background label

        // Create Swing components and arrange them using a layout manager
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 60px;'>Transaction Management</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

        viewHistoryButton= createColoredButton("View Transaction History");
        changeStateButton = createColoredButton("Change Transaction State");
        backButton = createColoredButton("Back");

        buttonContainer.add(viewHistoryButton);
        buttonContainer.add(changeStateButton);
        buttonContainer.add(backButton);
        viewHistoryButton.addActionListener(e -> handleViewTransactionHistory());;
        changeStateButton.addActionListener(e -> handleChangeTransactionState()); // Optional: Open separate window for managing tasks
        backButton.addActionListener(e -> this.dispose()); // Close window on back

        viewHistoryButton.setPreferredSize(new Dimension(100, 10));
        changeStateButton.setPreferredSize(new Dimension(100, 10));
        backButton.setPreferredSize(new Dimension(100, 10));


        changeStateButton = new JButton("Change Transaction State");
        add(changeStateButton);
        changeStateButton.addActionListener(e -> {
            try {
                handleChangeTransactionState();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });



        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add image to the left side
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/Image/twokids.png"));
        imageLabel.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800);
        setLocationRelativeTo(null);
        setVisible(true);


//        // Set layout manager for the frame
//        setLayout(new GridLayout(4, 1));
//
//        // Create Swing components
//        titleLabel = new JLabel("Transaction Management");
//        add(titleLabel);
//
//        viewHistoryButton = new JButton("View Transaction History");
//        add(viewHistoryButton);
//        viewHistoryButton.addActionListener(e -> handleViewTransactionHistory());
//
//        changeStateButton = new JButton("Change Transaction State");
//        add(changeStateButton);
//        changeStateButton.addActionListener(e -> handleChangeTransactionState());
//
//        backButton = new JButton("Back");
//        add(backButton);
//        backButton.addActionListener(e -> this.dispose()); // Close window on back
//
//        // Set frame properties
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pack();
//        setLocationRelativeTo(null); // Center the window on screen
//        setVisible(true);
    }

    private void handleViewTransactionHistory() {

        transSystem.viewTransactionHistory(); // Call existing method for console output (optional)
        new ViewTransactionHistoryWindow(transSystem).setVisible(true); // Open separate window for detailed history
    }

    private void handleChangeTransactionState() throws IOException, FontFormatException {
        if (transSystem.viewUncheckedTransactionHistory()) {
            // Open a separate window for selecting transaction and state change
            new ChangeTransactionStateWindow(transSystem, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No unreviewed transactions found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton createColoredButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24)); // Decrease font size
        button.setBackground(Color.ORANGE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setSize(new Dimension(100, -15)); // Adjust preferred size for the button
        button.setMargin(new Insets(30, 20, 30, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }
}


