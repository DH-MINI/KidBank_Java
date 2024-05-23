package com.group52.bank.model;
/**
 * This class represents a Parent user in the banking application.
 * A Parent user has a username and password.
 */
public class Parent extends User {
    /**
     * Constructs a new Parent with the given username and password.
     *
     * @param username the username
     * @param password the password
     */
    public Parent(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
    }
    /**
     * Constructs a new Parent with the given username, password, and a flag indicating whether hashing is needed.
     *
     * @param username the username
     * @param password the password
     * @param noNeedHash the flag indicating whether hashing is needed
     */

    public Parent(String username, String password, Boolean noNeedHash) {
        super("default","default_password");
        this.username = username;
        this.password = password;
    }

}
