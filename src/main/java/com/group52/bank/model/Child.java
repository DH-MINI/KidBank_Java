package com.group52.bank.model;

public class Child extends User {

    double balance;
    double savingGoal;

    public Child(String username, String password, double balance, double savingGoal) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
        this.balance = balance;
        this.savingGoal = savingGoal;
    }

    public Child(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
        this.balance = 0.0;
        this.savingGoal = 0.0;
    }

    public Child(String username, String password, Boolean noNeedHash) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.savingGoal = 0.0;
    }

    public Child(String username, String password, double balance, Boolean noNeedHash, double savingGoal) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.savingGoal = savingGoal;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSavingGoal(){ return savingGoal;}

    public boolean setSavingGoal(double savingGoal) {
        if ( this.getBalance() >= this.getSavingGoal() ) {
            this.savingGoal = savingGoal;
            return true;
        }
        else
            return false;
    }
}
