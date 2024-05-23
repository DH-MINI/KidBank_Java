package com.group52.bank.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class represents a Term Deposit transaction in the banking application.
 * A Term Deposit has a due date, profit rate, and duration in months.
 */
public class TermDeposit extends Transaction{
    private LocalDate due;
    private double profitRate; //利润率，写成小数形式
    private int months; //存几个月

    /**
     * Constructs a new TermDeposit with the given transactionId, amount, timestamp, type, source, destination, state, due date, profit rate, and duration in months.
     *
     * @param transactionId the transaction ID
     * @param amount the amount
     * @param timestamp the timestamp
     * @param type the type
     * @param source the source
     * @param destination the destination
     * @param state the state
     * @param due the due date
     * @param profitRate the profit rate
     * @param months the duration in months
     */
    public TermDeposit(String transactionId, double amount, LocalDateTime timestamp, String type, String source, String destination, String state, LocalDate due, double profitRate, int months){
        super(transactionId, amount, timestamp, type, source, destination, state);
        this.due = due;
        this.profitRate = profitRate;
        this.months = months;
    }

    /**
     * Returns the due date of the Term Deposit.
     *
     * @return the due date
     */
    //getters
    public LocalDate getDue(){
        return due;
    }
    /**
     * Returns the profit rate of the Term Deposit.
     *
     * @return the profit rate
     */
    public double getProfitRate(){
        return profitRate;
    }
    /**
     * Returns the duration in months of the Term Deposit.
     *
     * @return the duration in months
     */
    public int getMonths(){
        return months;
    }
    /**
     * Returns a string representation of the Term Deposit.
     *
     * @return a string representation of the Term Deposit
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + getTransactionId() + '\'' +
                ", amount='" + getAmount() +
                ", timestamp='" + getTimestamp() +
                ", type='" + getType() + '\'' +
                ", source='" + getSource() + '\'' +
                ", destination='" + getDestination() + '\'' +
                ", state='" + getState() + '\'' +
                ", months='" + getMonths() + '\'' +
                ", due='" + getDue() + '\'' +
                ", profitRate='" + getProfitRate() + '\'' +
                '}';
    }
}
