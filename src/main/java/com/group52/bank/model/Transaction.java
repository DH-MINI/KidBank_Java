package com.group52.bank.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * This class represents a Transaction in the banking application.
 * A Transaction has a transactionId, amount, timestamp, type, source, destination, and state.
 */
public class Transaction {

    private String transactionId;
    private double amount;
    private LocalDateTime timestamp;
    private String type; // Deposit Withdrawal or Term deposit
    private String source; // Where the transaction is from
    private String destination; // Where the transaction is going to
    private String state;
    /**
     * Constructs a new Transaction with the given transactionId, amount, timestamp, type, source, destination, and state.
     *
     * @param transactionId the transaction ID
     * @param amount the amount
     * @param timestamp the timestamp
     * @param type the type
     * @param source the source
     * @param destination the destination
     * @param state the state
     */
    public Transaction(String transactionId, double amount, LocalDateTime timestamp, String type, String source, String destination, String state) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.state = state;
    }
    /**
     * Returns the transaction ID of the Transaction.
     *
     * @return the transaction ID
     */
    // Getter methods
    public String getTransactionId() { return transactionId; }
    /**
     * Returns the amount of the Transaction.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Returns the timestamp of the Transaction.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    /**
     * Returns the type of the Transaction.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * Returns the source of the Transaction.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }
    /**
     * Returns the destination of the Transaction.
     *
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Returns the state of the Transaction.
     *
     * @return the state
     */
    public String getState() { return state; }
    /**
     * Returns a string representation of the Transaction.
     *
     * @return a string representation of the Transaction
     */
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
    /**
     * Confirms the request of the Transaction.
     */

    public void confirmRequest() {
        if (this.state.equals("Unchecked")) {
            this.state = "Confirmed";
        }
    }
    /**
     * Rejects the request of the Transaction.
     */
    public void rejectRequest() {
        if (this.state.equals("Unchecked")) {
            this.state = "Rejected";
        }
    }
    /**
     * Checks for the maturity of the Term Deposit.
     *
     * @return true if the Term Deposit has matured, false otherwise
     */
    //for Term Deposit Maturity
    public boolean TDmaturity(){
        this.state = "Maturity";
        return true;
    }
}
