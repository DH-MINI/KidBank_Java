package com.group52.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParentTest {

    @Test
    public void testConstructor() {
        Parent parent = new Parent("username", "password");
        assertEquals("username", parent.getUsername());
        assertNotEquals("password", parent.getPassword());
    }
}
