package com.group52.bank.task;

import com.group52.bank.model.Task;
import com.group52.bank.model.Transaction;
import com.group52.bank.model.Child;
import com.group52.bank.model.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Task System in the banking application.
 * It manages tasks, their history, and unreceived tasks.
 */
public class TaskSystem {

    private final String taskHistoryCSV;
    private final String childCSV;
    private final List<Task> taskHistory;
    private List<Task> unreceivedTasks;

    /**
     * Constructs a new TaskSystem with the given taskHistoryCSV and childCSV.
     *
     * @param taskHistoryCSV the task history CSV
     * @param childCSV the child CSV
     */
    public TaskSystem(String taskHistoryCSV, String childCSV) {
        this.taskHistoryCSV = taskHistoryCSV;
        this.childCSV = childCSV;
        this.taskHistory = loadTaskHistory();
        this.unreceivedTasks = loadUnrecTaskHistory();
    }

    /**
     * Displays the task history.
     */
    public void viewTaskHistory() {
        System.out.println("Task History:");
        for (Task task : taskHistory) {
            System.out.println(task.getDetails());
        }
    }

    /**
     * Pushes a new task with the given description, reward, and deadline.
     *
     * @param description the description
     * @param reward the reward
     * @param deadline the deadline
     */
    public void pushTask(String description, double reward, LocalDate deadline) {
        // Generate task ID (you may implement this based on your requirements)
        String taskId = "TASK_" + System.currentTimeMillis();

        Task task = new Task(taskId, description, reward, deadline);
        taskHistory.add(task);
        System.out.println("Task successfully published!");

        // Save the updated task history to CSV
        saveTaskHistory();
        loadTaskHistory();
        unrecTaskHistoryUpdate();
    }

    /**
     * Changes the state of the task with the given taskId and newState.
     *
     * @param taskId the task ID
     * @param newState the new state
     * @param user the user
     * @return true if the state was changed successfully, false otherwise
     */
    public boolean changeTaskState(String taskId, String newState, User user) {
        int count = 0;
        for (Task task : taskHistory) {
            if (task.getTaskId().contains(taskId)) {
                count++;
                if (count > 1) {
                    System.out.println("Multiple tasks found with the given ID. Please provide a more specific ID.");
                    return false;
                }
                if ("Complete".equals(newState) && task.getState().equals("ChildConfirmed")) {
                    task.doubleCheck();
                    updateChildBalance(task);
                } else if ("ChildComplete".equals(newState) && task.getReceivedBy().equals(user.getUsername())) {
                    task.childConfirmComplete();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return false;
                }
            }
        }
        saveTaskHistory();
        if (count == 0) {
            System.out.println("Task ID not found.");
            return false;
        }
        return true;
    }

    private void updateChildBalance(Task task) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (task.getReceivedBy().equals(parts[0])) {
                    double currentBalance = Double.parseDouble(parts[2]);
                    double savingGoal = parts.length > 3 ? Double.parseDouble(parts[3]) : 0.0;
                    currentBalance += task.getReward();
                    lines.add(parts[0] + "," + parts[1] + "," + currentBalance + "," + savingGoal);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing updated child balance to CSV: " + e.getMessage());
        }
    }

    /**
     * Receives the task with the given taskId and childUsername.
     *
     * @param taskId the task ID
     * @param childUsername the child username
     * @return true if the task was received successfully, false otherwise
     */
    public boolean receiveTask(String taskId, String childUsername) {
        int count = 0;
        for (Task task : taskHistory) {
            if (task.getTaskId().contains(taskId)) {
                count++;
                task.setReceivedBy(childUsername);
            }
        }
        unrecTaskHistoryUpdate();
        if (count == 1) {
            System.out.println(count + " task(s) received successfully.");
            return true;
        } else {
            System.out.println("Task ID not found or multiple Task ID found.");
            return false;
        }
    }

    /**
     * Loads the task history from the CSV file.
     *
     * @return the task history
     */
    private List<Task> loadTaskHistory() {
        List<Task> history = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(taskHistoryCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String taskId = data[0];
                String description = data[1];
                double reward = Double.parseDouble(data[2]);
                LocalDate deadline = LocalDate.parse(data[3]);
                String state = data[4];
                String receivedBy = data[5];
                history.add(new Task(taskId, description, reward, deadline, state, receivedBy));
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error loading task history from CSV: " + e.getMessage());
        }
        return history;
    }

    private List<Task> loadUnrecTaskHistory() {
        List<Task> unreceivedTasks = new ArrayList<>();
        for (Task task : taskHistory) {
            if ("Unreceived".equals(task.getReceivedBy())) {
                unreceivedTasks.add(task);
            }
        }
        if (unreceivedTasks.isEmpty()) {
            System.out.println("No unreceived tasks found.");
        }
        return unreceivedTasks;
    }

    /**
     * Saves the task history.
     */
    public void saveTaskHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(taskHistoryCSV))) {
            for (Task task : taskHistory) {
                bw.write(taskToCSV(task));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving task history to CSV: " + e.getMessage());
        }
    }

    private String taskToCSV(Task task) {
        return String.join(",", task.getTaskId(),
                task.getDescription(),
                String.valueOf(task.getReward()),
                task.getDeadline().toString(),
                task.getState(),
                task.getReceivedBy());
    }

    /**
     * Returns the task history for a specific child.
     *
     * @param child the child
     * @return the task history
     */
    public List<Task> getChildsTask(Child child) {
        List<Task> myTask = new ArrayList<>();
        for (Task task : taskHistory) {
            if (child.getUsername().equals(task.getReceivedBy())) {
                myTask.add(task);
            }
        }
        if (myTask.isEmpty()) {
            System.out.println("No tasks found.");
        }
        return myTask;
    }

    /**
     * Each time taskHistory updates, this method should be called.
     */
    public void unrecTaskHistoryUpdate() {
        unreceivedTasks = loadUnrecTaskHistory();
    }

    /**
     * Returns the task history.
     *
     * @return the task history
     */
    public List<Task> getTaskHistory() {
        return taskHistory;
    }

    /**
     * Returns the unreceived tasks.
     *
     * @return the unreceived tasks
     */
    public List<Task> getUnreceivedTasks() {
        return unreceivedTasks;
    }
}
