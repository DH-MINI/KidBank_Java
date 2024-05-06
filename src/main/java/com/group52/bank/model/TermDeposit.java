package com.group52.bank.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TermDeposit extends Transaction{
    private LocalDate due;
    private double profitRate; //利润率，写成小数形式
    private int months; //存几个月

    public TermDeposit(String transactionId, double amount, LocalDateTime timestamp, String type, String source, String destination, String state, LocalDate due, double profitRate, int months){
        super(transactionId, amount, timestamp, type, source, destination, state);
        this.due = due;
        this.profitRate = profitRate;
        this.months = months;
    }

    //getters
    public LocalDate getDue(){
        return due;
    }
    public double getProfitRate(){
        return profitRate;
    }
    public int getMonths(){
        return months;
    }

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
