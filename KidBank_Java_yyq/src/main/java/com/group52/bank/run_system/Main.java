package com.group52.bank.run_system;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.*;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";
    private static final String TRANSACTION_HISTORY_CSV = "src/main/resources/datacsv/transactionHistory.csv";
    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        AuthenticationSystem authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
        TransactionSystem transSystem = new TransactionSystem(TRANSACTION_HISTORY_CSV,CHILD_CSV);
        TaskSystem taskSystem = new TaskSystem(TASK_CSV,CHILD_CSV);

        List<Child> children = new ArrayList<>(authSystem.loadChildrenData());


        while (true) {
            System.out.println("Welcome to the Children's Banking App!");
            System.out.println("1. Login");
            System.out.println("2. Register (Parent)");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    User user = authSystem.login(username, password);
                    if (user != null) {
                        if (user instanceof Parent) {
                            handleParentMenu(scanner, (Parent) user, children, authSystem, transSystem, taskSystem);
                        } else if (user instanceof Child) {
                            handleChildMenu(scanner, authSystem.findChildByUsername(user.getUsername()), transSystem, taskSystem);
                        } else {
                            System.out.println("Invalid user type detected.");
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 2:
                    registerParent(scanner, authSystem);
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerParent(Scanner scanner, AuthenticationSystem authSystem) throws Exception {
        System.out.println("Parent Registration:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authSystem.register(new Parent(username, password))) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }

    private static void registerChild(Scanner scanner, AuthenticationSystem authSystem) throws Exception {
        System.out.println("Child Registration:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authSystem.register(new Child(username, password))) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }

    private static void handleParentMenu(Scanner scanner, Parent parent, List<Child> children, AuthenticationSystem authSystem, TransactionSystem transSystem, TaskSystem taskSystem) throws Exception {
        while (true) {
            System.out.println("\nParent Menu:");
            System.out.println("1. View Children");
            System.out.println("2. Create Child Account");
            System.out.println("3. Transaction Management");
            System.out.println("4. Task Management");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    children = new ArrayList<>(authSystem.loadChildrenData());
                    if (children.isEmpty()) {
                        System.out.println("No children registered yet.");
                    } else {
                        for (int i = 0; i < children.size(); i++) {
                            Child child = children.get(i);
                            System.out.println((i + 1) + ". " + child.getUsername() + " (Balance: $" + child.getBalance() + ")");
                        }
                    }
                    break;
                case 2:
                    registerChild(scanner, authSystem);
                    break;

                case 3:
                    handleTransactionManagement(scanner, transSystem);
                    break;

                case 4:
                    handleTaskManagementSubMenu(scanner, taskSystem);
                    break;


                case 5:
                    System.out.println("Logging out...");
                    transSystem.saveTransactionHistory();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }

    private static void handleTransactionManagement(Scanner scanner, TransactionSystem transSystem) {
        while (true) {
            System.out.println("\nTransaction Management:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Change Transaction State");
            System.out.println("3. Back");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    transSystem.viewTransactionHistory();
                    break;

                case 2:
                    if (transSystem.viewUncheckedTransactionHistory()){
                        System.out.println("Enter transaction ID to change state:");
                        String transactionId = scanner.nextLine();
                        System.out.println("Enter 'C' to confirm or 'R' to reject:");
                        String action = scanner.nextLine().toUpperCase();
                        action = action.equals("C") ? "Confirmed" : action;
                        action = action.equals("R") ? "Rejected" : action;
                        if (transSystem.changeTransactionState(transactionId, action)) {
                            System.out.println("Transaction state changed successfully.");
                        } else {
                            System.out.println("Failed to change transaction state. Transaction ID not found.");
                        }
                    }
                    else
                        break;
                    break;

                case 3:
                    transSystem.saveTransactionHistory();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void handleTaskManagementSubMenu(Scanner scanner, TaskSystem taskSystem) {
        while (true) {
            System.out.println("\nTask Management Submenu:");
            System.out.println("1. Publish Task");
            System.out.println("2. Manage Tasks");
            System.out.println("3. View Task History");
            System.out.println("4. Back to Parent Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Publish Task
                    System.out.println("Publish Task:");
                    System.out.print("Enter Task Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Reward: $");
                    double reward = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Deadline (yyyy-MM-dd): ");
                    LocalDate deadline;
                    try {
                        deadline = LocalDate.parse(scanner.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        break;
                    }
                    taskSystem.pushTask(description, reward, deadline);
                    break;

                case 2:
                    // Manage Tasks
                    taskSystem.viewTaskHistory();
                    System.out.println("\n");
                    System.out.println("Enter task ID to change state:");
                    String taskId = scanner.nextLine();
                    System.out.println("Enter new state (Complete/Delete):");
                    String newState = scanner.nextLine();
                    if (taskSystem.changeTaskState(taskId, newState)) {
                        System.out.println("Task state changed successfully.");
                        taskSystem.saveTaskHistory();
                    } else {
                        System.out.println("Failed to change task state. Task ID not found.");
                    }
                    break;


                case 3:
                    taskSystem.viewTaskHistory();
                    break;
                case 4:
                    System.out.println("Returning to Parent Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void handleChildMenu(Scanner scanner, Child child, TransactionSystem transSystem, TaskSystem taskSystem) {
        while (true) {
            System.out.println("\nChild Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. View Transaction history");
            System.out.println("3. Deposit and withdraw");
            System.out.println("4. Tasks Management");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Your balance: $" + child.getBalance());
                    break;

                case 2:
                    transSystem.viewTransactionHistory();
                    break;

                case 3:
                    System.out.println("Enter transaction amount:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter 'D' for Deposit or 'W' for Withdrawal:");
                    String transactionType = scanner.nextLine().toUpperCase();
                    String transactionId = "TRANS_" + System.currentTimeMillis();
                    String transactionSource = "Bank";
                    String transactionDestination = child.getUsername();
                    String transactionState = "Unchecked";

                    if (transactionType.equals("D")) {
                        // Deposit
                        Transaction depositTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Deposit", transactionSource, transactionDestination, transactionState);
                        transSystem.addTransaction(depositTransaction);
                        System.out.println("Send deposit request successful.");
                    } else if (transactionType.equals("W")) {
                        // Withdraw
                        if (amount > child.getBalance()) {
                            System.out.println("Insufficient funds for withdrawal.");
                        } else {
                            Transaction withdrawalTransaction = new Transaction(transactionId, amount, LocalDateTime.now(), "Withdrawal", transactionDestination, transactionSource, transactionState);
                            transSystem.addTransaction(withdrawalTransaction);
                            System.out.println("Send withdrawal request successful.");
                        }
                    } else {
                        System.out.println("Invalid transaction type. Please enter 'D' for Deposit or 'W' for Withdrawal.");
                    }
                    transSystem.saveTransactionHistory();
                    break;

                case 4:
                    handleChildTaskMenu(scanner, child, taskSystem);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    transSystem.saveTransactionHistory();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void handleChildTaskMenu(Scanner scanner, Child child, TaskSystem taskSystem) {
        while (true) {
            System.out.println("\nTask Management:");
            System.out.println("1. View Tasks");
            System.out.println("2. Receive Task");
            System.out.println("3. Confirm Task Completion");
            System.out.println("4. Back");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    taskSystem.viewTaskHistory();
                    break;

                case 2:
                    System.out.println("Enter task ID to receive:");
                    String receiveTaskId = scanner.nextLine();
                    if (taskSystem.receiveTask(receiveTaskId, child.getUsername())) {
                        taskSystem.saveTaskHistory();
                        System.out.println("Task received successfully.");
                    } else {
                        System.out.println("Failed to receive task. Task ID not found.");
                    }
                    break;


                case 3:
                    taskSystem.viewTaskHistory();
                    System.out.println("\n");
                    System.out.println("Enter task ID to change state:");
                    String taskId = scanner.nextLine();
                    if (taskSystem.changeTaskState(taskId, "ChildComplete")) {
                        taskSystem.saveTaskHistory();
                        System.out.println("Task confirm complete successfully.");
                    } else {
                        System.out.println("Failed to change task state. Task ID not found.");
                    }
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

