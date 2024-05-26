package com.group52.bank.GUI;

import com.group52.bank.model.Parent;
import com.group52.bank.model.Child;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;
import com.group52.bank.authentication.AuthenticationSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the parent menu window in the banking application.
 */
public class ParentMenuWindow extends JFrame {
    private ChildrensBankingApp app;
    private Parent parent;
    private TransactionSystem transSystem;
    private TaskSystem taskSystem;

    private JLabel titleLabel;
    private JButton viewChildrenButton;
    private JButton createChildAccountButton;
    private JButton transactionManagementButton;
    private JButton taskManagementButton;
    private JButton setProfitButton;
    private JButton logoutButton;
    private JTable childTable;
    /**
     * Constructs a new ParentMenuWindow with the given banking application, parent, transaction system, and task system.
     *
     * @param app the banking application
     * @param parent the parent user
     * @param transSystem the transaction system
     * @param taskSystem the task system
     */
    public ParentMenuWindow(ChildrensBankingApp app, Parent parent, TransactionSystem transSystem, TaskSystem taskSystem) {
        super("Parent Menu - Welcome, " + parent.getUsername());
        this.app = app;
        this.parent = parent;
        this.transSystem = transSystem;
        this.taskSystem = taskSystem;

        // Load background image
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);

        // Ensure layout manager of JLabel is null to freely position components
        backgroundLabel.setLayout(null);

        setLayout(new BorderLayout());

        // Title label setup
        titleLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='font-size: 60px;'>Parent Menu</span><br/>" +
                "<span style='font-size: 40px;  color: #0097D9;'>Welcome, " + parent.getUsername() + "</span>" +
                "</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28)); // This size is overridden by HTML

        // Create a JPanel to hold buttons
        JPanel buttonContainer = new JPanel();
        buttonContainer.setOpaque(false); // Set the background to be transparent
        buttonContainer.setLayout(new GridLayout(6, 1, 0, 30)); // Use GridLayout for vertical arrangement with gaps
        buttonContainer.setBounds(700, 150, 250, 500); // Adjust position and size

        backgroundLabel.add(buttonContainer); // Add the container to the background label

        // Create buttons
        viewChildrenButton = createColoredButton("View Children");
        createChildAccountButton = createColoredButton("Create Child Account");
        transactionManagementButton = createColoredButton("Transaction Management");
        taskManagementButton = createColoredButton("Task Management");
        setProfitButton = createColoredButton("Set Profit Rate of Term Deposit");
        logoutButton = createColoredButton("Logout");

        // Add buttons to the container
        buttonContainer.add(viewChildrenButton);
        buttonContainer.add(createChildAccountButton);
        buttonContainer.add(transactionManagementButton);
        buttonContainer.add(taskManagementButton);
        buttonContainer.add(setProfitButton);
        buttonContainer.add(logoutButton);

        // Setup event handlers for buttons
        setupEventHandlers();

        // Add image to the left side
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/Image/childmenu1.png"));
        imageLabel.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.WEST);

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
        viewChildrenButton.addActionListener(e -> handleViewChildren());
        createChildAccountButton.addActionListener(e -> handleCreateChildAccount());
        transactionManagementButton.addActionListener(e -> handleTransactionManagement());
        taskManagementButton.addActionListener(e -> handleTaskManagementSubMenu());
        setProfitButton.addActionListener(e -> handleSetProfit());
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
        button.setBackground(new Color(173, 216, 230)); // Light blue color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 30)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }
    /**
     * Handles the view children action.
     */
    private void handleViewChildren() {
        List<Child> children = new ArrayList<>(app.authSystem.loadChildrenData());
        UIManager.put("OptionPane.okButtonText", "Confirm");
        if (children.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No children registered yet.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Create a 2D array of child information (username, balance, etc.)
            String[][] childData = new String[children.size()][];
            for (int i = 0; i < children.size(); i++) {
                Child child = children.get(i);
                childData[i] = new String[]{child.getUsername(), String.valueOf(child.getBalance())}; // Add more elements as needed
            }

            // Create column names for the table
            String[] columnNames = {"Username", "Balance"};

            // Create a JTable model and set the data and column names
            TableModel tableModel = new DefaultTableModel(childData, columnNames);
            childTable = new JTable(tableModel);

            // Add the table to a JScrollPane if needed
            JScrollPane scrollPane = new JScrollPane(childTable);
            JOptionPane.showMessageDialog(this, scrollPane, "Children List", JOptionPane.PLAIN_MESSAGE);
        }
    }
    /**
     * Handles the create child account action.
     */
    private void handleCreateChildAccount() {
        // Open a separate window for child registration
        new CreateChildAccountWindow(app.authSystem, this).setVisible(true);
    }
    /**
     * Handles the transaction management action.
     */
    private void handleTransactionManagement() {
        // Open a separate window for transaction management sub-menu
        new TransactionMenuWindow(app.transSystem, this).setVisible(true);
    }
    /**
     * Handles the task management sub-menu action.
     */
    private void handleTaskManagementSubMenu() {
        // Open a separate window for task management sub-menu
        new TaskMenuWindow(app.taskSystem, this, parent).setVisible(true);
    }
    /**
     * Handles the logout action.
     */
    private void handleLogout() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            app.transSystem.saveTransactionHistory();
            this.dispose(); // Close current window
            app.loginWindow.setVisible(true); // Show login window again
        }
    }
    /**
     * Handles the set profit action.
     */
    private void handleSetProfit() {
        new SetProfitRateWindow(transSystem);
    }
}


