package com.group52.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChildTest {

    @Test
    public void testGetBalance() {
        Child child = new Child("username", "password", 100.0);
        assertEquals(100.0, child.getBalance());
    }

    @Test
    public void testSetBalance() {
        Child child = new Child("username", "password");
        child.setBalance(50.0);
        assertEquals(50.0, child.getBalance());
    }

    @Test
    public void testConstructorWithBalance() {
        Child child = new Child("username", "password", 200.0);
        assertEquals("username", child.getUsername());
        assertNotEquals("password", child.getPassword());
        assertEquals(200.0, child.getBalance());
    }

    @Test
    public void testConstructorWithoutBalance() {
        Child child = new Child("username", "password");
        assertEquals("username", child.getUsername());
        assertNotEquals("password", child.getPassword());
        assertEquals(0.0, child.getBalance());
    }
}
