package com.group52.bank.model;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private double amount;
    private LocalDateTime timestamp;
    private String type; // Deposit or Withdrawal
    private String source; // Where the transaction is from
    private String destination; // Where the transaction is going to

    private String state;

    public Transaction(String transactionId, double amount, LocalDateTime timestamp, String type, String source, String destination, String state) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.state = state;
    }

    // Getter methods
    public String getTransactionId() { return transactionId; }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getType() {
        return type;
    }
    public String getSource() {
        return source;
    }
    public String getDestination() {
        return destination;
    }
    public String getState() { return state; }
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", state=" + state + '\'' +
                '}';
    }

    public void confirmRequest() {
        if (this.state.equals("Unchecked")) {
            this.state = "Confirmed";
        }
    }

    public void rejectRequest() {
        if (this.state.equals("Unchecked")) {
            this.state = "Rejected";
        }
    }

}
