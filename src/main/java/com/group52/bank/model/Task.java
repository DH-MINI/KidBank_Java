package com.group52.bank.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * This class represents a Task in the banking application.
 * A Task has a taskId, description, reward, deadline, state, and receivedBy.
 */
public class Task {

    private String taskId;
    private String description;
    private String receivedBy;
    private double reward;
    private LocalDate deadline;
    private String state;

    /**
     * Constructs a new Task with the given taskId, description, reward, and deadline.
     *
     * @param taskId the taskId
     * @param description the description
     * @param reward the reward
     * @param deadline the deadline
     */
    public Task(String taskId, String description, double reward, LocalDate deadline) {
        this.taskId = taskId;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
        this.state = "Uncompleted";
        this.receivedBy = "Unreceived";
    }
    /**
     * Constructs a new Task with the given taskId, description, reward, deadline, state, and receivedBy.
     *
     * @param taskId the taskId
     * @param description the description
     * @param reward the reward
     * @param deadline the deadline
     * @param state the state
     * @param receivedBy the receivedBy
     */
    public Task(String taskId, String description, double reward, LocalDate deadline, String state, String receivedBy) {
        this.taskId = taskId;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
        this.state = state;
        this.receivedBy = receivedBy;
    }
    /**
     * Returns the details of the Task.
     *
     * @return the details of the Task
     */
    public String getDetails() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "Task ID: " + taskId + ", Description: " + description + ", Received by: " + receivedBy +", Reward: $" + reward + ", Deadline: " + deadlineString + ", Status: " + state;
    }
    /**
     * Confirms the completion of the Task by the child.
     */
    public void childConfirmComplete() {
        if (this.state.equals("Uncompleted")){
            this.state = "ChildConfirmed";
        }
    }
    /**
     * Double checks the completion of the Task.
     */
    public void doubleCheck() {
        if (this.state.equals("ChildConfirmed")){
            this.state = "Complete";
        }

    }
    /**
     * Returns the taskId of the Task.
     *
     * @return the taskId
     */
    public String getTaskId() {
        return this.taskId;
    }
    /**
     * Returns the description of the Task.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Returns the reward of the Task.
     *
     * @return the reward
     */
    public double getReward() {
        return this.reward;
    }
    /**
     * Returns the deadline of the Task.
     *
     * @return the deadline
     */
    public LocalDate getDeadline() {
        return this.deadline;
    }
    /**
     * Returns the state of the Task.
     *
     * @return the state
     */
    public String getState() {
        return this.state;
    }
    /**
     * Returns the receivedBy of the Task.
     *
     * @return the receivedBy
     */
    public String getReceivedBy() {
        return receivedBy;
    }
    /**
     * Sets the receivedBy of the Task.
     *
     * @param childUsername the new receivedBy
     */
    public void setReceivedBy(String childUsername) {
        this.receivedBy = childUsername;
    }
}
