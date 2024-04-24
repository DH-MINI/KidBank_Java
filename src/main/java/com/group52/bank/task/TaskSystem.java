package com.group52.bank.task;

import com.group52.bank.model.Task;
import com.group52.bank.model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskSystem {

    private final String taskHistoryCSV;
    private final String childCSV;
    private final List<Task> taskHistory;

    public TaskSystem(String taskHistoryCSV, String childCSV) {
        this.taskHistoryCSV = taskHistoryCSV;
        this.childCSV = childCSV;
        this.taskHistory = loadTaskHistory();
    }


    public void viewTaskHistory() {
        System.out.println("Task History:");
        for (Task task : taskHistory) {
            System.out.println(task.getDetails());
        }
    }

    public void pushTask(String description, double reward, LocalDate deadline) {
        // Generate task ID (you may implement this based on your requirements)
        String taskId = "TASK_" + System.currentTimeMillis();

        Task task = new Task(taskId, description, reward, deadline);
        taskHistory.add(task);
        System.out.println("Task successfully published!");

        // Save the updated task history to CSV
        saveTaskHistory();
        loadTaskHistory();
    }


    public boolean changeTaskState(String taskId, String newState) {
        int count = 0;
        for (Task task : taskHistory) {
            if (task.getTaskId().contains(taskId)) {
                count++;
                if (count > 1) {
                    System.out.println("Multiple tasks found with the given ID. Please provide a more specific ID.");
                    return false;
                }
                if ("Complete".equals(newState)) {
                    task.doubleCheck();
                } else if ("ChildComplete".equals(newState)) {
                    task.childConfirmComplete();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return false;
                }
            }
        }
        if (count == 0) {
            System.out.println("Task ID not found.");
            return false;
        }
        return true;
    }

    public boolean receiveTask(String taskId, String childUsername) {
        int count = 0;
        for (Task task : taskHistory) {
            if (task.getTaskId().contains(taskId)) {
                count++;
                task.setReceivedBy(childUsername);
            }
        }
        if (count == 1) {
            System.out.println(count + " task(s) received successfully.");
            return true;
        } else {
            System.out.println("Task ID not found or multiple Task ID found.");
            return false;
        }
    }

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

    public List<Task> getTaskHistory() {
        return taskHistory;
    }
}
