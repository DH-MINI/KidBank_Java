package com.group52.bank.model;

// Can be implemented within Child class or as a separate class

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private List<Transaction> transactions;

    public BankAccount() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

//    public void deposit(double amount) {
//        this.balance += amount;
//        transactions.add(new Transaction( /* generate transaction id */, amount, LocalDateTime.now(), "Deposit"));
//    }
//
//    public void withdraw(double amount) throws InsufficientFundsException {
//        if (amount > balance) {
//            throw new InsufficientFundsException("Insufficient funds for withdrawal");
//        }
//        this.balance -= amount;
//        transactions.add(new Transaction( /* generate transaction id */, amount, LocalDateTime.now(), "Withdrawal"));
//    }

    public double getBalance() {
        return balance;
    }
}
