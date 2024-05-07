package com.group52.bank.model;

public abstract class User {

    protected String username;
    protected String password; // Hashed password (implement secure hashing)

    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password); // Hash password before storing
    }

    public String getUsername() {
        return username;
    }

    private String hashPassword(String plainTextPassword) {
        // Implement a secure password hashing algorithm (replace with actual implementation)
        // Code by YYQ;
        String hPW = "";
        int len = plainTextPassword.length();
        for(int n = 0; n < len; n++) {
            String s1 = plainTextPassword.substring(n, len);
            int hc = s1.hashCode();
            String hs1 = Integer.toHexString(hc);
            hPW = hPW + hs1.substring(hs1.length()-2,hs1.length());
        }

        return "HASHED_" + hPW; // Placeholder for demonstration
    }

    private String inverseHashPassword(String hashedPassword) {
        if (hashedPassword.startsWith("HASHED_")) {
            return hashedPassword.substring("HASHED_".length());
        } else {
            return hashedPassword;
        }
    }

    public String getPassword() {
        //Modify by YYQ;
        //password = inverseHashPassword(password);
        return password;

    }

    // Code by YYQ;
    public boolean VerifyPassword(String PW) {
        String hPW = hashPassword(PW);
        return password.equals(hPW);
    }
}
