package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;
import com.group52.bank.authentication.AuthenticationSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class represents the child menu window in the banking application.
 */
public class ChildMenuWindow extends JFrame {
    private ChildrensBankingApp app;
    private Child child;
    private TransactionSystem transSystem;
    private TaskSystem taskSystem;

    private AuthenticationSystem authSystem;

    private JLabel titleLabel;
    private JButton viewBalanceButton;
    private JButton viewTransactionHistoryButton;
    private JButton depositWithdrawButton;
    private JButton taskManagementButton;
    private JButton logoutButton;

    private JButton savingGoalButton;
    /**
     * Constructs a new ChildMenuWindow with the given parameters.
     *
     * @param app the banking application
     * @param child the child user
     * @param transSystem the transaction system
     * @param taskSystem the task system
     * @param authSystem the authentication system
     */
    public ChildMenuWindow(ChildrensBankingApp app, Child child, TransactionSystem transSystem, TaskSystem taskSystem, AuthenticationSystem authSystem) {
        super("Child Menu - Welcome, " + child.getUsername());
        this.app = app;
        this.child = child;
        this.transSystem = transSystem;
        this.taskSystem = taskSystem;
        this.authSystem = authSystem;

        // Load background image
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Ensure layout manager of JLabel is null to freely position components
        backgroundLabel.setLayout(null);

        //getContentPane().setBackground(new Color(247, 246, 254)); // Set lavender background

        setLayout(new BorderLayout());

        // Title label setup
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 60px;'>Child Menu</span><br/>" +
                "<span style='font-size: 40px; color: orange;'>Welcome, " + child.getUsername() + "</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //buttonPanel.setOpaque(false); // Make the background transparent



        // Create a JPanel to hold buttons
        JPanel buttonContainer = new JPanel();
        buttonContainer.setOpaque(false); // Set the background to be transparent
        buttonContainer.setLayout(new GridLayout(5, 1, 0, 30)); // Use GridLayout for vertical arrangement with gaps
        backgroundLabel.add(buttonContainer); // Add the container to the background label

        // Create buttons
//        viewBalanceButton = createColoredButton("View Balance");
        savingGoalButton = createColoredButton("View Balance and Saving Goal");
        viewTransactionHistoryButton = createColoredButton("View Transaction History");
        depositWithdrawButton = createColoredButton("Deposit and Withdraw");
        taskManagementButton = createColoredButton("Task Management");
        logoutButton = createColoredButton("Logout");



        // Add buttons to the container
//        buttonContainer.add(viewBalanceButton);
        buttonContainer.add(savingGoalButton);
        buttonContainer.add(viewTransactionHistoryButton);
        buttonContainer.add(depositWithdrawButton);
        buttonContainer.add(taskManagementButton);
        buttonContainer.add(logoutButton);



        // Setup event handlers for buttons
        setupEventHandlers();

//        JPanel rightPanel = new JPanel(new BorderLayout());
//        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add image to the left side
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/Image/childmenu1.png"));
        imageLabel.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.WEST);
//        add(rightPanel, BorderLayout.EAST);

        // Frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Sets up the event handlers for the buttons.
     */
    private void setupEventHandlers() {
//        viewBalanceButton.addActionListener(e -> handleViewBalance());
        savingGoalButton.addActionListener(e -> {
            try {
                handleSavingGoalWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });
        viewTransactionHistoryButton.addActionListener(e -> handleViewTransactionHistory());
        depositWithdrawButton.addActionListener(e -> handleDepositWithdraw());
        taskManagementButton.addActionListener(e -> handleChildTaskMenuWindow());
        logoutButton.addActionListener(e -> handleLogout());
    }
    /**
     * Creates a colored button with the given text.
     *
     * @param text the text for the button
     * @return the created button
     */
    private JButton createColoredButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24)); // Decrease font size
        button.setBackground(Color.ORANGE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 30)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }

    /**
     * Handles the view balance action.
     */
    private void handleViewBalance() {
        JOptionPane.showMessageDialog(this, "Your balance is: $" + child.getBalance(), "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the view transaction history action.
     */
    private void handleViewTransactionHistory() {
        new ViewTransactionHistoryWindow(transSystem, child).setVisible(true);
    }
    /**
     * Handles the deposit and withdraw action.
     */
    private void handleDepositWithdraw() {
        new DepositWithdrawWindow(child, transSystem).setVisible(true);
    }
    /**
     * Handles the child task menu window action.
     */
    private void handleChildTaskMenuWindow() {
        new ChildTaskMenuWindow(taskSystem, this, child).setVisible(true);
    }

    /**
     * Handles the logout action.
     */
    private void handleLogout() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            this.dispose();
            app.loginWindow.setVisible(true);
        }
    }
    /**
     * Handles the saving goal window action.
     */
    private void handleSavingGoalWindow() throws IOException, FontFormatException {
        new SavingGoalWindow(child, transSystem).setVisible(true);

    }
}
