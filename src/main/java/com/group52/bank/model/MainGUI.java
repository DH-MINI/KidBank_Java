//package com.group52.bank.GUI;
//
//import com.group52.bank.authentication.AuthenticationSystem;
//import com.group52.bank.model.*;
//import com.group52.bank.task.TaskSystem;
//import com.group52.bank.transaction.TransactionSystem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//
//    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
//    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";
//    private static final String TRANSACTION_HISTORY_CSV = "src/main/resources/datacsv/transactionHistory.csv";
//    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";
//
//    private static AuthenticationSystem authSystem;
//    private static TransactionSystem transSystem;
//    private static TaskSystem taskSystem;
//    private static JFrame mainFrame;
//
//    public static void main(String[] args) throws Exception {
//        authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
//        transSystem = new TransactionSystem(TRANSACTION_HISTORY_CSV, CHILD_CSV);
//        taskSystem = new TaskSystem(TASK_CSV, CHILD_CSV);
//
//        initializeMainFrame();
//    }
//
//    private static void initializeMainFrame() {
//        mainFrame = new JFrame("Children's Banking App");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(400, 300);
//        mainFrame.setLocationRelativeTo(null); // Center the window
//
//        JPanel mainPanel = new JPanel(new FlowLayout());
//
//        // Login Panel
//        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField(15);
//        JLabel passwordLabel = new JLabel("Password:");
//        JTextField passwordField = new JTextField(15);
////        passwordField.setEchoChar('*');  // Hide password
//        JButton loginButton = new JButton("Login");
//        JButton registerButton = new JButton("Register");
//
//        loginPanel.add(usernameLabel);
//        loginPanel.add(usernameField);
//        loginPanel.add(passwordLabel);
//        loginPanel.add(passwordField);
//        loginPanel.add(loginButton);
//        loginPanel.add(registerButton);
//
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String username = usernameField.getText();
//                String password = passwordField.getText();
//                handleLogin(username, password);
//                usernameField.setText("");
//                passwordField.setText("");
//            }
//        });
//
//        registerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showRegisterPanel();
//            }
//        });
//
//        mainPanel.add(loginPanel);
//
//        mainFrame.add(mainPanel);
//        mainFrame.setVisible(true);
//    }
//
//    private static void handleLogin(String username, String password) {
//        User user = authSystem.login(username, password);
//        if (user != null) {
//            if (user instanceof Parent) {
//                showParentMenu((Parent) user);
//            } else if (user instanceof Child) {
//                showChildMenu((Child) user);
//            } else {
//                JOptionPane.showMessageDialog(mainFrame, "Invalid user type detected.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(mainFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private static void showRegisterPanel() {
//        JPanel registerPanel = new JPanel(new GridLayout(2, 2));
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField(15);
//        JLabel passwordLabel = new JLabel("Password:");
//        JTextField passwordField = new JTextField(15);
////        passwordField.setEchoChar('*');  // Hide password
//        JButton registerButton = new JButton("Register");
//
//        registerPanel.add(usernameLabel);
//        registerPanel.add(usernameField);
//        registerPanel.add(passwordLabel);
//        registerPanel.add(passwordField);  // Add the password field back to the panel
//        registerPanel.add(registerButton);
//
//        int result = JOptionPane.showConfirmDialog(mainFrame, registerPanel, "Register", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//            try {
//                if (authSystem.register(new Parent(username, password))) {
//                    JOptionPane.showMessageDialog(mainFrame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(mainFrame, "Registration failed. Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(mainFrame, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private static void showParentMenu(Parent parent) {
//        List<Child> children = new ArrayList<>(authSystem.loadChildrenData());
//        JFrame parentMenuFrame = new JFrame("Parent Menu");
//        parentMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Allow returning to main
//
//        JPanel parentMenuPanel = new JPanel();
//
//        // View Children
//        JButton viewChildrenButton = new JButton("View Children");
//        viewChildrenButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                StringBuilder childrenInfo = new StringBuilder("Your Children:\n");
//                for (Child child : children) {
//                    childrenInfo.append(String.format("- %s (balance: $%.2f)\n", child.getUsername(), child.viewBalance()));
//                }
//                JOptionPane.showMessageDialog(parentMenuFrame, childrenInfo.toString(), "Children Information", JOptionPane.INFORMATION_MESSAGE);
//            }
//        });
//        parentMenuPanel.add(viewChildrenButton);
//
//        // Create Child Account
//        JButton createChildAccountButton = new JButton("Create Child Account");
//        createChildAccountButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String username = JOptionPane.showInputDialog(parentMenuFrame, "Enter child's username:");
//                if (username != null && !username.isEmpty()) {
//                    String password = JOptionPane.showInputDialog(parentMenuFrame, "Enter child's password:");
//                    if (password != null && !password.isEmpty()) {
//                        try {
//                            if (authSystem.register(new Child(username, password))) {
//                                children.add(new Child(username, password));  // Update local list
//                                JOptionPane.showMessageDialog(parentMenuFrame, "Child account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                            } else {
//                                JOptionPane.showMessageDialog(parentMenuFrame, "Child account creation failed. Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            JOptionPane.showMessageDialog(parentMenuFrame, "Child account creation failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(parentMenuFrame, "Please enter a password for the child account.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(parentMenuFrame, "Please enter a username for the child account.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//        parentMenuPanel.add(createChildAccountButton);
//
//        // Transaction Management
//        JButton transactionManagementButton = new JButton("Transaction Management");
//        transactionManagementButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String childUsername = JOptionPane.showInputDialog(parentMenuFrame, "Enter child's username for transaction:");
//                if (childUsername != null && !childUsername.isEmpty()) {
//                    Child child = authSystem.findChildByUsername(childUsername);
//                    if (child != null) {
//                        showTransactionMenu(child);
//                    } else {
//                        JOptionPane.showMessageDialog(parentMenuFrame, "Invalid child username.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(parentMenuFrame, "Please enter a child's username.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//        parentMenuPanel.add(transactionManagementButton
//        );
//
//        // Task Management
//        JButton taskManagementButton = new JButton("Task Management");
//        taskManagementButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String childUsername = JOptionPane.showInputDialog(parentMenuFrame, "Enter child's username for task management:");
//                if (childUsername != null && !childUsername.isEmpty()) {
//                    Child child = authSystem.findChildByUsername(childUsername);
//                    if (child != null) {
//                        taskSystem.viewTaskHistory();
//                    } else {
//                        JOptionPane.showMessageDialog(parentMenuFrame, "Invalid child username.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(parentMenuFrame, "Please enter a child's username.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//        parentMenuPanel.add(taskManagementButton);
//
//        // Logout Button
//        JButton logoutButton = new JButton("Logout");
//        logoutButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                parentMenuFrame.dispose();
//                JOptionPane.showMessageDialog(mainFrame, "Logging out...", "Logout", JOptionPane.INFORMATION_MESSAGE);
//                transSystem.saveTransactionHistory();
//            }
//        });
//        parentMenuPanel.add(logoutButton);
//
//        parentMenuFrame.add(parentMenuPanel);
//        parentMenuFrame.pack();
//        parentMenuFrame.setLocationRelativeTo(null);
//        parentMenuFrame.setVisible(true);
//    }
//
//    private static void showChildMenu(Child child) {
//        JFrame childMenuFrame = new JFrame("Child Menu");
//        childMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel childMenuPanel = new JPanel();
//
//        // View Balance
//        JButton viewBalanceButton = new JButton("View Balance");
//        viewBalanceButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(childMenuFrame, String.format("Your balance: $%.2f", child.viewBalance()), "Balance", JOptionPane.INFORMATION_MESSAGE);
//            }
//        });
//        childMenuPanel.add(viewBalanceButton);
//
//        // View Transaction History
//        JButton viewTransactionHistoryButton = new JButton("View Transaction History");
//        viewTransactionHistoryButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                List<Transaction> transactions = transSystem.getTransactionHistory();
//                if (transactions.isEmpty()) {
//                    JOptionPane.showMessageDialog(childMenuFrame, "No transactions found in your history.", "Transaction History", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    StringBuilder history = new StringBuilder("Transaction History:\n");
//                    for (Transaction transaction : transactions) {
//                        history.append(String.format("- %s: $%.2f (%s)\n", transaction.getTimestamp().toString(), transaction.getAmount()));
//                    }
//                    JTextArea historyTextArea = new JTextArea(history.toString(), 10, 30);
//                    historyTextArea.setEditable(false);
//                    JScrollPane scrollPane = new JScrollPane(historyTextArea);
//                    JOptionPane.showMessageDialog(childMenuFrame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//        childMenuPanel.add(viewTransactionHistoryButton);
//
//        // Deposit/Withdraw
//        JButton depositWithdrawButton = new JButton("Deposit/Withdraw");
//        depositWithdrawButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String action = (String) JOptionPane.showInputDialog(childMenuFrame, "Choose action:", "Deposit/Withdraw", JOptionPane.DEFAULT_OPTION, null, new String[]{"Deposit", "Withdraw"});
//                if (action != null) {
//                    String amountStr = JOptionPane.showInputDialog(childMenuFrame, "Enter amount:");
//                    if (amountStr != null && !amountStr.isEmpty()) {
//                        double amount;
//                        try {
//                            amount = Double.parseDouble(amountStr);
//                            if (amount > 0) {
//                                if (action.equals("Deposit")) {
//                                    transSystem.deposit(child.getUsername(), amount, "Deposit");
//                                    child.setBalance(child.getBalance() + amount);  // Update local balance
//                                    JOptionPane.showMessageDialog(childMenuFrame, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                                } else if (action.equals("Withdraw")) {
//                                    if (amount <= child.
//                                            getBalance()) {
//                                        transSystem.withdraw(child.getUsername(), amount, "Withdraw");
//                                        child.setBalance(child.getBalance() - amount);  // Update local balance
//                                        JOptionPane.showMessageDialog(childMenuFrame, "Withdraw successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                                    } else {
//                                        JOptionPane.showMessageDialog(childMenuFrame, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
//                                    }
//                                }
//                            } else {
//                                JOptionPane.showMessageDialog(childMenuFrame, "Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
//                            }
//                        } catch (NumberFormatException ex) {
//                            JOptionPane.showMessageDialog(childMenuFrame, "Invalid amount format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(childMenuFrame, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        });
//        childMenuPanel.add(depositWithdrawButton);
//
//        // Tasks Management
//        JButton tasksMenuButton = new JButton("Tasks Management");
//        tasksMenuButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showTaskMenu(child);
//            }
//        });
//        childMenuPanel.add(tasksMenuButton);
//
//        // Logout Button
//        JButton logoutButton = new JButton("Logout");
//        logoutButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                childMenuFrame.dispose();
//                JOptionPane.showMessageDialog(mainFrame, "Logging out...", "Logout", JOptionPane.INFORMATION_MESSAGE);
//                transSystem.saveTransactionHistory();
//            }
//        });
//        childMenuPanel.add(logoutButton);
//
//        childMenuFrame.add(childMenuPanel);
//        childMenuFrame.pack();
//        childMenuFrame.setLocationRelativeTo(null);
//        childMenuFrame.setVisible(true);
//    }
//
//    private static void showTransactionMenu(Child child) {
//        JFrame transactionMenuFrame = new JFrame("Transaction Management");
//        transactionMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel transactionMenuPanel = new JPanel();
//
//        // View Transaction History (same as in Child Menu)
//        JButton viewTransactionHistoryButton = new JButton("View Transaction History");
//        viewTransactionHistoryButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                List<Transaction> transactions = transSystem.getTransactionHistory(child.getUsername());
//                if (transactions.isEmpty()) {
//                    JOptionPane.showMessageDialog(transactionMenuFrame, "No transactions found in child's history.", "Transaction History", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    StringBuilder history = new StringBuilder("Transaction History:\n");
//                    for (Transaction transaction : transactions) {
//                        history.append(String.format("- %s: $%.2f (%s)\n", transaction.getDate().toString(), transaction.getAmount(), transaction.getDescription()));
//                    }
//                    JTextArea historyTextArea = new JTextArea(history.toString(), 10, 30);
//                    historyTextArea.setEditable(false);
//                    JScrollPane scrollPane = new JScrollPane(historyTextArea);
//                    JOptionPane.showMessageDialog(transactionMenuFrame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//        transactionMenuPanel.add(viewTransactionHistoryButton);
//
//        // Deposit (same as in Child Menu)
//        JButton depositButton = new JButton("Deposit");
//        depositButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String amountStr = JOptionPane.showInputDialog(transactionMenuFrame, "Enter amount to deposit:");
//                if (amountStr != null && !amountStr.isEmpty()) {
//                    double amount;
//                    try {
//                        amount = Double.parseDouble(amountStr);
//                        if (amount > 0) {
//                            transSystem.deposit(child.getUsername(), amount, "Deposit by Parent");
//                            child.setBalance(child.getBalance() + amount);  // Update local balance
//                            JOptionPane.showMessageDialog(transactionMenuFrame, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                        } else {
//                            JOptionPane.showMessageDialog(transactionMenuFrame, "Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
//                        }
//                    } catch (NumberFormatException ex) {
//                        JOptionPane.showMessageDialog(transactionMenuFrame, "Invalid amount format. Please enter a number.", "Error", JOptionPane.ERROR
//                                MESSAGE);
//                    }
//                }
//            });
//        taskMenuPanel.add(addTaskButton);
//
//            // Mark Task Complete
//            JButton markTaskCompleteButton = new JButton("Mark Task Complete");
//        markTaskCompleteButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    List<Task> tasks = taskSystem.getTasks(child.getUsername());
//                    if (tasks.isEmpty()) {
//                        JOptionPane.showMessageDialog(taskMenuFrame, "No tasks found for this child.", "Tasks", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        StringBuilder taskList = new StringBuilder("Tasks:\n");
//                        int taskCount = 1;
//                        for (Task task : tasks) {
//                            taskList.append(String.format("%d. %s: %s (%s)\n", taskCount, task.getTitle(), task.getDescription(), task.isCompleted() ? "Completed" : "Pending"));
//                            taskCount++;
//                        }
//                        String taskChoiceStr = JOptionPane.showInputDialog(taskMenuFrame, taskList.toString() + "\nEnter the number of the task to mark complete:");
//                        if (taskChoiceStr != null && !taskChoiceStr.isEmpty()) {
//                            try {
//                                int taskChoice = Integer.parseInt(taskChoiceStr);
//                                if (taskChoice > 0 && taskChoice <= tasks.size()) {
//                                    Task task = tasks.get(taskChoice - 1);
//                                    if (!task.isCompleted()) {
//                                        taskSystem.completeTask(task.getId());
//                                        JOptionPane.showMessageDialog(taskMenuFrame, "Task marked complete successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                                    } else {
//                                        JOptionPane.showMessageDialog(taskMenuFrame, "Selected task is already completed.", "Information", JOptionPane.INFORMATION_MESSAGE);
//                                    }
//                                } else {
//                                    JOptionPane.showMessageDialog(taskMenuFrame, "Invalid task number entered.", "Error", JOptionPane.ERROR_MESSAGE);
//                                }
//                            } catch (NumberFormatException ex) {
//                                JOptionPane.showMessageDialog(taskMenuFrame, "Invalid task number format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
//                            }
//                        }
//                    }
//                }
//            });
//        taskMenuPanel.add(markTaskCompleteButton);
//
//        taskMenuFrame.add(taskMenuPanel);
//        taskMenuFrame.pack();
//        taskMenuFrame.setLocationRelativeTo(null);
//        taskMenuFrame.setVisible(true);
//        }
//    }
//
//
