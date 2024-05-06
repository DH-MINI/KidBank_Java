package com.group52.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TDTest {

    @Test
    public void testToString() {
        LocalDateTime timestamp = LocalDateTime.now();
        LocalDate due = LocalDate.of(2024, 10, 6);
        TermDeposit termDeposit = new TermDeposit("TD001", 1000.0, timestamp, "Deposit", "Savings", "Term Deposit", "Pending", due, 0.05, 12);
        String expectedString = "Transaction{transactionId='TD001', amount='1000.0, timestamp='" + timestamp + "', type='Deposit', source='Savings', destination='Term Deposit', state='Pending', months='12', due='" + due + "', profitRate='0.05'}";
        assertEquals(expectedString, termDeposit.toString());
    }
}
