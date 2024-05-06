package com.group52.bank.GUI;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.task.TaskSystem;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;

public class ChildrensBankingApp extends JFrame {

    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";
    private static final String TRANSACTION_HISTORY_CSV = "src/main/resources/datacsv/transactionHistory.csv";
    private static final String TASK_CSV = "src/main/resources/datacsv/taskHistory.csv";

    AuthenticationSystem authSystem;
    TransactionSystem transSystem;
    TaskSystem taskSystem;
    LoginWindow loginWindow;

    public ChildrensBankingApp() throws Exception {
        super("Children's Banking App");

        // Initialize authentication, transaction, and task systems (use your existing code)
        authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
        transSystem = new TransactionSystem(TRANSACTION_HISTORY_CSV, CHILD_CSV);
        taskSystem = new TaskSystem(TASK_CSV, CHILD_CSV);

        // Create the login window
        loginWindow = new LoginWindow(this);
        loginWindow.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new ChildrensBankingApp();
    }
}
