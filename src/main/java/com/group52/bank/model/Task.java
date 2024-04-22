package com.group52.bank.model;

import java.time.LocalDate;

public class Task {

    private String taskId;
    private String description;
    private double reward;
    private LocalDate deadline;

    public Task(String taskId, String description, double reward, LocalDate deadline) {
        this.taskId = taskId;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
    }

    public String getDetails() {
        return "Task ID: " + taskId + ", Description: " + description + ", Reward: $" + reward + ", Deadline: " + deadline;
    }
}
