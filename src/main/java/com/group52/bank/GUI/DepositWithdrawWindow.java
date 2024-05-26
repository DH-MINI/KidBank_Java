package com.group52.bank.GUI;

import com.group52.bank.model.Child;
import com.group52.bank.transaction.TransactionSystem;
import com.group52.bank.model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
/**
 * This class represents a window for depositing and withdrawing in the banking application.
 */
public class DepositWithdrawWindow extends JFrame {

    private Child child;
    private TransactionSystem transSystem;

    private JLabel titleLabel;
    private JLabel amountLabel;
    private JTextField amountField;
    private JRadioButton depositRadioButton;
    private JRadioButton TDRadioButton;
    private JRadioButton withdrawRadioButton;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel balanceLabel;
    /**
     * Constructs a new DepositWithdrawWindow with the given child and transaction system.
     *
     * @param child the child user
     * @param transSystem the transaction system
     */
    public DepositWithdrawWindow(Child child, TransactionSystem transSystem) {
        super("Deposit and Withdraw - Welcome, " + child.getUsername());
        this.child = child;
        this.transSystem = transSystem;

        // Load background image
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Ensure layout manager of JLabel is null to freely position components
        backgroundLabel.setLayout(null);

        // Title label setup
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 40px;'>Deposit and Withdraw</span><br/>" +
                "<span style='font-size: 30px; color: orange;'>Welcome, " + child.getUsername() + "</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setBounds(150, 20, 1000, 100); // Adjust position and size
        backgroundLabel.add(titleLabel);

        // Balance label setup
        balanceLabel = new JLabel("Account Balance: " + child.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setBounds(400, 150, 500, 50); // Adjust position and size
        backgroundLabel.add(balanceLabel);

        // Amount label setup
        amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        amountLabel.setBounds(350, 250, 150, 50); // Adjust position and size
        backgroundLabel.add(amountLabel);

        // Amount field setup
        amountField = new JTextField(10);
        amountField.setFont(new Font("Arial", Font.PLAIN, 30));
        amountField.setBounds(520, 250, 300, 50); // Adjust position and size
        backgroundLabel.add(amountField);

        // Radio buttons setup
        depositRadioButton = new JRadioButton("Deposit");
        depositRadioButton.setFont(new Font("Arial", Font.PLAIN, 30));
        depositRadioButton.setOpaque(false);

        TDRadioButton = new JRadioButton("Term Deposit");
        TDRadioButton.setToolTipText("<html><span style='font-size:16px;'>A term deposit is a fixed-term investment where you deposit a certain amount of money for a specific period,<br> and you earn a fixed interest rate on your deposit. You cannot withdraw the money before the term ends without incurring penalties.</span></html>");
        TDRadioButton.setFont(new Font("Arial", Font.PLAIN, 30));
        TDRadioButton.setOpaque(false);

        withdrawRadioButton = new JRadioButton("Withdraw");
        withdrawRadioButton.setFont(new Font("Arial", Font.PLAIN, 30));
        withdrawRadioButton.setOpaque(false);

        // Set deposit radio button selected by default
        depositRadioButton.setSelected(true);

        // Create button group for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(depositRadioButton);
        buttonGroup.add(TDRadioButton);
        buttonGroup.add(withdrawRadioButton);

        // Create a panel for radio buttons
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setOpaque(false);
        radioButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        radioButtonPanel.add(depositRadioButton);
        radioButtonPanel.add(TDRadioButton);
        radioButtonPanel.add(withdrawRadioButton);
        radioButtonPanel.setBounds(300, 350, 600, 50); // Adjust position and size
        backgroundLabel.add(radioButtonPanel);

        // Submit and Cancel buttons setup
        submitButton = createColoredButton("Submit");
        cancelButton = createColoredButton("Cancel");

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBounds(350, 500, 500, 60); // Adjust position and size
        backgroundLabel.add(buttonPanel);

        // Insert image at the bottom left corner
        ImageIcon depositImage = new ImageIcon("src/main/resources/Image/depositimage.png");
        JLabel depositImageLabel = new JLabel(depositImage);
        Dimension imageSize = new Dimension(depositImage.getIconWidth(), depositImage.getIconHeight());
        depositImageLabel.setSize(imageSize);
        depositImageLabel.setLocation(0, backgroundLabel.getHeight() - depositImageLabel.getHeight());
        backgroundLabel.add(depositImageLabel);

        // Setup event handlers for buttons
        setupEventHandlers();

        // Frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1290, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        // Ensure the image is correctly positioned after the window is displayed
        SwingUtilities.invokeLater(() -> {
            depositImageLabel.setLocation(0, backgroundLabel.getHeight() - depositImageLabel.getHeight());
        });
    }
    /**
     * Sets up the event handlers for the buttons.
     */
    private void setupEventHandlers() {
        submitButton.addActionListener(e -> handleTransaction());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel
    }
    /**
     * Creates a colored button with the given text.
     *
     * @param text the text for the button
     * @return the created button
     */
    private JButton createColoredButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 30)); // Adjusted font size
        button.setBackground(new Color(255, 204, 102)); // Light yellow button color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }
    /**
     * Handles the transaction.
     */
    private void handleTransaction() {
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String transactionId = "TRANS_" + System.currentTimeMillis();
        String transactionSource = child.getUsername();
        String transactionDestination = "Bank";
        String transactionState = "Unchecked";

        if (depositRadioButton.isSelected()) {
            // Deposit
            Transaction depositTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Deposit", transactionSource, transactionDestination, transactionState);
            transSystem.addTransaction(depositTransaction);
            JOptionPane.showMessageDialog(this, "Deposit request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (TDRadioButton.isSelected()) {
            new TDcheckDetailWindow(child, transSystem, amount);
        } else if (withdrawRadioButton.isSelected()) {
            // Withdraw
            if (amount > child.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Transaction withdrawalTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Withdrawal", transactionDestination, transactionSource, transactionState);
                transSystem.addTransaction(withdrawalTransaction);
                JOptionPane.showMessageDialog(this, "Withdrawal request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select Deposit or Withdraw.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        transSystem.saveTransactionHistory();
        this.dispose(); // Close window after transaction
    }
}
