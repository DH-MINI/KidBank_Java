package com.group52.bank.model;

import com.group52.bank.task.TaskSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskSystemTest {

    private TaskSystem taskSystem;
    private final String taskHistoryCSV = "src/main/resources/TestDataCsv/testTaskHistory.csv";
    private final String childCSV = "src/main/resources/TestDataCsv/testChild.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Set up test CSV files with initial data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(taskHistoryCSV))) {
            bw.write("TASK_1,Description1,50.0,2024-12-31,Uncompleted,Unreceived");
            bw.newLine();
            bw.write("TASK_2,Description2,30.0,2024-11-30,Uncompleted,Unreceived");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            bw.write("Child1,password,500.0,1000.0");
            bw.newLine();
            bw.write("Child2,password,300.0,600.0");
        }

        taskSystem = new TaskSystem(taskHistoryCSV, childCSV);
    }

    @Test
    void testLoadTaskHistory() {
        List<Task> taskHistory = taskSystem.getTaskHistory();
        assertEquals(2, taskHistory.size());

        Task task1 = taskHistory.get(0);
        assertEquals("TASK_1", task1.getTaskId());
        assertEquals("Description1", task1.getDescription());
        assertEquals(50.0, task1.getReward());
        assertEquals(LocalDate.of(2024, 12, 31), task1.getDeadline());
        assertEquals("Uncompleted", task1.getState());
        assertEquals("Unreceived", task1.getReceivedBy());

        Task task2 = taskHistory.get(1);
        assertEquals("TASK_2", task2.getTaskId());
        assertEquals("Description2", task2.getDescription());
        assertEquals(30.0, task2.getReward());
        assertEquals(LocalDate.of(2024, 11, 30), task2.getDeadline());
        assertEquals("Uncompleted", task2.getState());
        assertEquals("Unreceived", task2.getReceivedBy());
    }

    @Test
    void testPushTask() {
        taskSystem.pushTask("Description3", 20.0, LocalDate.of(2024, 10, 31));
        List<Task> taskHistory = taskSystem.getTaskHistory();
        assertEquals(3, taskHistory.size());

        Task task = taskHistory.get(2);
        assertEquals("Description3", task.getDescription());
        assertEquals(20.0, task.getReward());
        assertEquals(LocalDate.of(2024, 10, 31), task.getDeadline());
        assertEquals("Uncompleted", task.getState());
        assertEquals("Unreceived", task.getReceivedBy());
    }

    @Test
    void testReceiveTask() {
        assertTrue(taskSystem.receiveTask("TASK_1", "Child1"));
        List<Task> taskHistory = taskSystem.getTaskHistory();
        Task task = taskHistory.get(0);
        assertEquals("Child1", task.getReceivedBy());

        assertFalse(taskSystem.receiveTask("TASK_3", "Child1")); // Non-existent task
    }

    @Test
    void testChangeTaskState() {
        User child = new Child("Child1", "password", 500.0, true, 1000.0);
        assertTrue(taskSystem.receiveTask("TASK_1", "Child1"));
        assertTrue(taskSystem.changeTaskState("TASK_1", "ChildComplete", child));

        List<Task> taskHistory = taskSystem.getTaskHistory();
        Task task = taskHistory.get(0);
        assertEquals("ChildConfirmed", task.getState());

        assertTrue(taskSystem.changeTaskState("TASK_1", "Complete", new Parent("Parent1", "password", true)));
        assertEquals("Complete", task.getState());

        assertFalse(taskSystem.changeTaskState("TASK_1", "InvalidState", child)); // Invalid state change
        assertFalse(taskSystem.changeTaskState("TASK_2", "ChildComplete", child)); // Task not received by user
    }

    @Test
    void testUpdateChildBalance() {
        User parent = new Parent("Parent1", "password", true);
        assertTrue(taskSystem.receiveTask("TASK_1", "Child1"));
        assertTrue(taskSystem.changeTaskState("TASK_1", "ChildComplete", new Child("Child1", "password", 500.0, true, 1000.0)));
        assertTrue(taskSystem.changeTaskState("TASK_1", "Complete", parent));

        List<String> childLines = readFile(childCSV);
        assertEquals(2, childLines.size());
        assertTrue(childLines.get(0).contains("550.0")); // Updated balance for Child1
    }

    private List<String> readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }
}
