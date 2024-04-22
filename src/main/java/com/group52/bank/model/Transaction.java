package com.group52.bank.model;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private double amount;
    private LocalDateTime timestamp;
    private String type; // Deposit or Withdrawal

    public Transaction(String transactionId, double amount, LocalDateTime timestamp, String type) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
    }

    // Simulate transaction execution (may not be necessary)
    public void execute() {
        // ...
    }
}
