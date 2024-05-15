package com.group52.bank.model;

public class Parent extends User {

    public Parent(String username, String password) {
        super("default","default_password");
        this.username = username;
        this.password = hashPassword(password);
    }

    public Parent(String username, String password, Boolean noNeedHash) {
        super("default","default_password");
        this.username = username;
        this.password = password;
    }

}
