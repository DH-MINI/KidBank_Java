package com.group52.bank.transaction;

import com.group52.bank.model.Transaction;

import java.io.*;
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
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
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
                // Parse LocalDateTime from data[2] if needed
                String type = data[3];
                String source = data[4];
                String destination = data[5];
                String state = data[6];
                history.add(new Transaction(transactionId, amount, null, type, source, destination, state));
            }
        } catch (IOException | NumberFormatException e) {
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
}
