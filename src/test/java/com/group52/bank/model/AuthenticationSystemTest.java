package com.group52.bank.model;

import com.group52.bank.authentication.AuthenticationSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationSystemTest {

    private AuthenticationSystem authenticationSystem;
    private final String parentCSV = "src/main/resources/TestDataCsv/testParent.csv";
    private final String childCSV = "src/main/resources/TestDataCsv/testChild.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Set up test CSV files with initial data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(parentCSV))) {
            bw.write("Parent1,password1");
            bw.newLine();
            bw.write("Parent2,password2");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            bw.write("Child1,password,500.0,1000.0");
            bw.newLine();
            bw.write("Child2,password,300.0,600.0");
        }

        authenticationSystem = new AuthenticationSystem(parentCSV, childCSV);
    }

    @Test
    void testLoadUsersFromCSV() {
        List<Child> children = authenticationSystem.loadChildrenData();
        assertEquals(2, children.size());

        Child child1 = children.get(0);
        assertEquals("Child1", child1.getUsername());
        assertEquals("password", child1.getPassword());
        assertEquals(500.0, child1.getBalance());
        assertEquals(1000.0, child1.getSavingGoal());

        Child child2 = children.get(1);
        assertEquals("Child2", child2.getUsername());
        assertEquals("password", child2.getPassword());
        assertEquals(300.0, child2.getBalance());
        assertEquals(600.0, child2.getSavingGoal());
    }

    @Test
    void testFindChildByUsername() {
        Child child = authenticationSystem.findChildByUsername("Child1");
        assertNotNull(child);
        assertEquals("Child1", child.getUsername());

        Child nonExistentChild = authenticationSystem.findChildByUsername("NonExistentChild");
        assertNull(nonExistentChild);
    }

    @Test
    void testRegister() {
        User newParent = new Parent("Parent3", "password3", true);
        assertTrue(authenticationSystem.register(newParent));

        User newChild = new Child("Child3", "password", 200.0, true, 500.0);
        assertTrue(authenticationSystem.register(newChild));

        User existingParent = new Parent("Parent1", "newpassword", true);
        assertFalse(authenticationSystem.register(existingParent));

        User existingChild = new Child("Child1", "newpassword", 100.0, true, 200.0);
        assertFalse(authenticationSystem.register(existingChild));
    }

}
