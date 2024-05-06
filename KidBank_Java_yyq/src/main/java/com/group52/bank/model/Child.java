package com.group52.bank.model;

public class Child extends User {

    double balance;

    public Child(String username, String password, double balance) {
        super(username,password);
        this.balance = balance;
    }

    public Child(String username, String password) {
        super(username,password);
        this.balance = 0.0;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
