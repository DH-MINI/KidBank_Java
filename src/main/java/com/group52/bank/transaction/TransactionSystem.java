package com.group52.bank.transaction;

import com.group52.bank.model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionSystem {

    private String transactionHistoryCSV;
    private List<Transaction> transactionHistory;

    public TransactionSystem(String transactionHistoryCSV) {
        this.transactionHistoryCSV = transactionHistoryCSV;
        this.transactionHistory = loadTransactionHistory();
    }

    public List<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
        saveTransactionHistory();
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.toString());
        }
    }
    private List<Transaction> loadTransactionHistory() {
        List<Transaction> history = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(transactionHistoryCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String transactionId = data[0];
                double amount = Double.parseDouble(data[1]);
                LocalDateTime timestamp = LocalDateTime.parse(data[2]); // Parse LocalDateTime
                String type = data[3];
                String source = data[4];
                String destination = data[5];
                String state = data[6];
                history.add(new Transaction(transactionId, amount, timestamp, type, source, destination, state));
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error loading transaction history from CSV: " + e.getMessage());
        }
        return history;
    }

    public void saveTransactionHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionHistoryCSV))) {
            for (Transaction transaction : transactionHistory) {
                bw.write(transaction.getTransactionId() + "," +
                        transaction.getAmount() + "," +
                        transaction.getTimestamp() + "," +
                        transaction.getType() + "," +
                        transaction.getSource() + "," +
                        transaction.getDestination() + "," +
                        transaction.getState());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction history to CSV: " + e.getMessage());
        }
    }

    public boolean changeTransactionState(String transactionId, String newState) {
        int count = 0;
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionId().contains(transactionId)) {
                count++;
                if (count > 1) {
                    // 如果找到的交易 ID 不止一个，直接返回 false
                    System.out.println("Multiple transactions found with the given ID. Please provide a more specific ID.");
                    return false;
                }
                if (newState.equals("Confirmed")) {
                    transaction.confirmRequest();
                } else if (newState.equals("Rejected")) {
                    transaction.rejectRequest();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return false;
                }
            }
        }
        if (count == 0) {
            // 如果没有找到任何交易，返回 false
            System.out.println("Transaction ID not found.");
            return false;
        }
        return true;
    }

}



