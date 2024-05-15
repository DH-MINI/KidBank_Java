package com.group52.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class TaskTest {

    @Test
    public void testGetDetails() {
        LocalDate deadline = LocalDate.of(2024, 5, 10);
        Task task = new Task("T001", "Buy groceries", 50.0, deadline);
        String expectedDetails = "Task ID: T001, Description: Buy groceries, Received by: Unreceived, Reward: $50.0, Deadline: 2024-05-10, Status: Uncompleted";
        assertEquals(expectedDetails, task.getDetails());
    }

    @Test
    public void testChildConfirmComplete() {
        LocalDate deadline = LocalDate.of(2024, 5, 10);
        Task task = new Task("T001", "Buy groceries", 50.0, deadline);
        task.childConfirmComplete();
        assertEquals("ChildConfirmed", task.getState());
    }

    @Test
    public void testDoubleCheck() {
        LocalDate deadline = LocalDate.of(2024, 5, 10);
        Task task = new Task("T001", "Buy groceries", 50.0, deadline);
        task.childConfirmComplete();
        task.doubleCheck();
        assertEquals("Complete", task.getState());
    }

    @Test
    public void testSetReceivedBy() {
        LocalDate deadline = LocalDate.of(2024, 5, 10);
        Task task = new Task("T001", "Buy groceries", 50.0, deadline);
        task.setReceivedBy("childUsername");
        assertEquals("childUsername", task.getReceivedBy());
    }
}
