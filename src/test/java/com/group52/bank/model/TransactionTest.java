package com.group52.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class TransactionTest {

    @Test
    public void testConfirmRequest() {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction("T001", 100.0, timestamp, "Deposit", "AccountA", "AccountB", "Unchecked");
        transaction.confirmRequest();
        assertEquals("Confirmed", transaction.getState());
    }

    @Test
    public void testRejectRequest() {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction("T002", 200.0, timestamp, "Withdrawal", "AccountC", "AccountD", "Unchecked");
        transaction.rejectRequest();
        assertEquals("Rejected", transaction.getState());
    }

    @Test
    public void testTDmaturity() {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction("T003", 300.0, timestamp, "Deposit", "AccountE", "Term Deposit", "Unchecked");
        assertTrue(transaction.TDmaturity());
        assertEquals("Maturity", transaction.getState());
    }

}
