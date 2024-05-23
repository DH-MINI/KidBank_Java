package com.group52.bank.model;
/**
 * This class represents a Child user in the banking application.
 * A Child user has a balance and a saving goal.
 */
public class Child extends User {

    double balance;
    double savingGoal;
    /**
     * Constructs a new Child with the given username, password, balance, and saving goal.
     *
     * @param username the username
     * @param password the password
     * @param balance the balance
     * @param savingGoal the saving goal
     */
    public Child(String username, String password, double balance, double savingGoal) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
        this.balance = balance;
        this.savingGoal = savingGoal;
    }
    /**
     * Constructs a new Child with the given username and password.
     *
     * @param username the username
     * @param password the password
     */
    public Child(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
        this.balance = 0.0;
        this.savingGoal = 0.0;
    }
    /**
     * Constructs a new Child with the given username, password, and a flag indicating whether hashing is needed.
     *
     * @param username the username
     * @param password the password
     * @param noNeedHash the flag indicating whether hashing is needed
     */
    public Child(String username, String password, Boolean noNeedHash) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.savingGoal = 0.0;
    }
    /**
     * Constructs a new Child with the given username, password, balance, a flag indicating whether hashing is needed, and saving goal.
     *
     * @param username the username
     * @param password the password
     * @param balance the balance
     * @param noNeedHash the flag indicating whether hashing is needed
     * @param savingGoal the saving goal
     */
    public Child(String username, String password, double balance, Boolean noNeedHash, double savingGoal) {
        super("default","default_password");
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.savingGoal = savingGoal;
    }

    /**
     * Returns the balance of the Child.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }
    /**
     * Sets the balance of the Child.
     *
     * @param balance the new balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * Returns the saving goal of the Child.
     *
     * @return the saving goal
     */
    public double getSavingGoal(){ return savingGoal;}
    /**
     * Sets the saving goal of the Child.
     *
     * @param savingGoal the new saving goal
     * @return true if the saving goal is set successfully, false otherwise
     */
    public boolean setSavingGoal(double savingGoal) {
        if ( this.getBalance() >= this.getSavingGoal() ) {
            this.savingGoal = savingGoal;
            return true;
        }
        else
            return false;
    }
}
