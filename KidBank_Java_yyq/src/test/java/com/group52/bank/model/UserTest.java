package com.group52.bank.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        String username = "test_user";
        String password = "password123";
        User user = new Child(username, password);

        assertNotNull(user);
        assertEquals(username, user.getUsername()); // Optional, if getters are implemented
    }

    @Test
    public void testPasswordHashing() {
        String password = "plain_text_password";
        User user = new Child("test_user", password);

        // This assertion is a placeholder, replace with actual logic to verify hashed password
        assertNotEquals(password, user.getPassword()); // Hashed password should not be the same as plain text
    }

    // Add more test cases for other functionalities of User class (optional)
}
