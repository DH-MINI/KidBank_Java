package com.group52.bank.model;

public class Child extends User {

    double balance;

    public Child(String username, String password, double balance) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
//yyq

    public Child(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
