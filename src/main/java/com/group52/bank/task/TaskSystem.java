package com.group52.bank.task;

import com.group52.bank.model.Task;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskSystem {

    private String taskHistoryCSV;
    private String childCSV;
    private List<Task> taskHistory;

    public TaskSystem(String taskHistoryCSV, String childCSV) {
        this.taskHistoryCSV = taskHistoryCSV;
        this.taskHistory = loadTaskHistory();
        this.childCSV = childCSV;
    }

    public List<Task> getTaskHistory() {
        return taskHistory;
    }

    public void addTask(Task task) {
        taskHistory.add(task);
        saveTaskHistory();
    }

    public void viewTaskHistory() {
        System.out.println("Task History:");
        for (Task task : taskHistory) {
            System.out.println(task.getDetails());
        }
    }
    public void pushTask(Scanner scanner) {
        System.out.println("\nPublish Task:");
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
            return;
        }

        // Generate task ID (you may implement this based on your requirements)
        String taskId = "TASK_" + System.currentTimeMillis();

        Task task = new Task(taskId, description, reward, deadline);
        taskHistory.add(task);
        System.out.println("Task successfully published!");

        // Save the updated task history to CSV
        saveTaskHistory();
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
                LocalDate deadline = LocalDate.parse(data[3]); // Parse LocalDate
                history.add(new Task(taskId, description, reward, deadline));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading task history from CSV: " + e.getMessage());
        }
        return history;
    }

    public void saveTaskHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(taskHistoryCSV))) {
            for (Task task : taskHistory) {
                bw.write(task.getTaskId() + "," +
                        task.getDescription() + "," +
                        task.getReward() + "," +
                        task.getDeadline());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving task history to CSV: " + e.getMessage());
        }
    }
}
