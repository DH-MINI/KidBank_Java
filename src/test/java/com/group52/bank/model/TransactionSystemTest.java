package com.group52.bank.model;
import com.group52.bank.transaction.TransactionSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionSystemTest {

    private TransactionSystem transactionSystem;
    private final String transactionHistoryCSV = "src/main/resources/TestDataCsv/testTransactionHistory.csv";
    private final String childCSV = "src/main/resources/TestDataCsv/testChild.csv";
    private final String profitRateCSV = "src/main/resources/TestDataCsv/testProfitRate.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Set up test CSV files with initial data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionHistoryCSV))) {
            bw.write("T1,100.0,2023-05-24T10:15:30,Deposit,Source,Destination,Unchecked");
            bw.newLine();
            bw.write("T2,200.0,2023-05-25T11:16:30,Withdrawal,Source,Destination,Confirmed");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            bw.write("Child1,password,500.0,1000.0");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(profitRateCSV))) {
            bw.write("0.05");
        }

        transactionSystem = new TransactionSystem(transactionHistoryCSV, childCSV, profitRateCSV);
    }

    @Test
    void testAddTransaction() {
        Transaction transaction = new Transaction("T3", 300.0, LocalDateTime.now(), "Deposit", "Source", "Destination", "Unchecked");
        transactionSystem.addTransaction(transaction);

        List<Transaction> transactions = transactionSystem.getTransactionHistory();
        assertEquals(3, transactions.size());
        assertEquals("T3", transactions.get(2).getTransactionId());
    }

    @Test
    void testViewUncheckedTransactionHistory() {
        boolean hasUnchecked = transactionSystem.viewUncheckedTransactionHistory();
        assertTrue(hasUnchecked);
    }

    @Test
    void testChangeTransactionState() {
        boolean result = transactionSystem.changeTransactionState("T1", "Confirmed");
        assertTrue(result);

        Transaction transaction = transactionSystem.getTransactionHistory().stream()
                .filter(t -> "T1".equals(t.getTransactionId()))
                .findFirst()
                .orElse(null);

        assertNotNull(transaction);
        assertEquals("Confirmed", transaction.getState());
    }

    @Test
    void testGetCurrentProfitRate() {
        double profitRate = transactionSystem.getCurrentProfitRate();
        assertEquals(0.05, profitRate);
    }

    @Test
    void testSetProfitRate() {
        boolean result = transactionSystem.setProfitRate(0.06);
        assertTrue(result);

        double profitRate = transactionSystem.getCurrentProfitRate();
        assertEquals(0.06, profitRate);
    }

    @Test
    void testUpdateChildBalance() {
        Transaction transaction = new Transaction("T4", 100.0, LocalDateTime.now(), "Deposit", "Source", "Child1", "Unchecked");
        transactionSystem.addTransaction(transaction);
        transactionSystem.changeTransactionState("T4", "Confirmed");

        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line = br.readLine();
            assertNotNull(line);
            String[] parts = line.split(",");
            assertEquals("Child1", parts[0]);
            assertEquals("600.0", parts[2]);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testUpdateChildSavingGoal() {
        transactionSystem.updateChildSavingGoal("Child1", 1500.0);

        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line = br.readLine();
            assertNotNull(line);
            String[] parts = line.split(",");
            assertEquals("Child1", parts[0]);
            assertEquals("1500.0", parts[3]);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
