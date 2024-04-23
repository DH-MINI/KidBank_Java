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

    public boolean changeTaskState(String taskId, String newState) {
        int count = 0;
        for (Task task : taskHistory) {
            if (task.getTaskId().contains(taskId)) {
                count++;
                if (count > 1) {
                    // 如果找到的任务 ID 不止一个，直接返回 false
                    System.out.println("Multiple tasks found with the given ID. Please provide a more specific ID.");
                    return false;
                }
                if (newState.equals("Complete")) {
                    // 如果状态为 "Complete"，则标记任务为完成状态
                    task.doubleCheck();
//                } else if (newState.equals("Delete")) {
//                    // 如果状态为 "Uncompleted"，则标记任务为未完成状态
//                    task.markAsUncompleted();
                    
                } else if (newState.equals("ChildComplete")) {
                    task.childConfirmComplete();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return false;
                }
            }
        }
        if (count == 0) {
            // 如果没有找到任何任务，返回 false
            System.out.println("Task ID not found.");
            return false;
        }
        return true;
    }

    public boolean receiveTask(String taskId, String childUsername) {
        for (Task task : taskHistory) {
            if (task.getTaskId().equals(taskId)) {
                task.setReceivedBy(childUsername);
                return true;
            }
        }
        System.out.println("Task ID not found.");
        return false;
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
                String state = data[4];
                String receivedBy = data[5];
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
                        task.getDeadline() + "," +
                        task.getState() + "," +
                        task.getReceivedBy());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving task history to CSV: " + e.getMessage());
        }
    }
}
