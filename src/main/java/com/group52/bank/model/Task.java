package com.group52.bank.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private String taskId;
    private String description;
    private String receivedBy;
    private double reward;
    private LocalDate deadline;
    private String state;

    public Task(String taskId, String description, double reward, LocalDate deadline) {
        this.taskId = taskId;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
        this.state = "Uncompleted";
        this.receivedBy = "Unreceived";
    }

    public Task(String taskId, String description, double reward, LocalDate deadline, String state, String receivedBy) {
        this.taskId = taskId;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
        this.state = state;
        this.receivedBy = receivedBy;
    }

    public String getDetails() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "Task ID: " + taskId + ", Description: " + description + ", Received by: " + receivedBy +", Reward: $" + reward + ", Deadline: " + deadlineString + ", Status: " + state;
    }

    public void childConfirmComplete() {
        if (this.state.equals("Uncompleted")){
            this.state = "ChildConfirmed";
        }
    }

    public void doubleCheck() {
        if (this.state.equals("ChildConfirmed")){
            this.state = "Complete";
        }

    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getDescription() {
        return this.description;
    }

    public double getReward() {
        return this.reward;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public String getState() {
        return this.state;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String childUsername) {
        this.receivedBy = childUsername;
    }
}
