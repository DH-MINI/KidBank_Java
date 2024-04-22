package com.group52.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User {

    private List<Child> childAccounts;

    public Parent(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.childAccounts = new ArrayList<>();
    }

    public void publishTask(Task task, Child child) {
        child.taskList.add(task);
    }

    public double viewChildBalance(Child child) {
        return child.balance;
    }

    public List<Transaction> viewTransactions(Child child) {
        // Implement logic to retrieve child's transaction history
        return null; // Replace with actual implementation
    }

    @Override
    public boolean login() {
        // Implement login logic using username and password
        return true;
    }
}
