package com.group52.bank.model;


import java.util.ArrayList;
import java.util.List;

public class Child extends User {

//    final ArrayList<Object> taskList;
List<Task> taskList;
    double balance;

    public Child(String username, String password, double balance) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.taskList = new ArrayList<>();
        this.balance = balance;
    }

    public Child(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.taskList = new ArrayList<>();
        this.balance = 0.0;
    }


    public void selectTask(String taskId) {
        // Implement logic to select a specific task
    }

    public void completeTask(String taskId) {
        // Implement logic to mark a task as completed
    }

    public double viewBalance() {
        return balance;
    }

    @Override
    public boolean login() {
        // Implement login logic using username and password
        return false;
    }
}
