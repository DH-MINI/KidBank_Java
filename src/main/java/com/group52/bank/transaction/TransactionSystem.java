package com.group52.bank.transaction;

import com.group52.bank.model.Child;
import com.group52.bank.model.TermDeposit;
import com.group52.bank.model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Transaction System in the banking application.
 * It manages transactions, their history, and unchecked transactions.
 */
public class TransactionSystem {

    private final String transactionHistoryCSV;
    private final String childCSV;
    private final String profitRateCSV;
    private final List<Transaction> transactionHistory;
    private List<Transaction> uncheckedTransHistory;

    /**
     * Constructs a new TransactionSystem with the given transactionHistoryCSV, childCSV, and profitRateCSV.
     *
     * @param transactionHistoryCSV the transaction history CSV
     * @param childCSV the child CSV
     * @param profitRateCSV the profit rate CSV
     */
    public TransactionSystem(String transactionHistoryCSV, String childCSV, String profitRateCSV) {
        this.transactionHistoryCSV = transactionHistoryCSV;
        this.childCSV = childCSV;
        this.profitRateCSV = profitRateCSV;
        this.transactionHistory = loadTransactionHistory();
        this.uncheckedTransHistory = loadUncheckedTransHistory();
    }

    /**
     * Adds a new transaction to the transaction history.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        if ("Unchecked".equals(transaction.getState())) {
            uncheckedTransHistory.add(transaction);
        }
        saveTransactionHistory();
    }

    /**
     * Displays the transaction history.
     */
    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.toString());
        }
    }

    /**
     * Displays the unchecked transaction history.
     *
     * @return true if there are unchecked transactions, false otherwise
     */
    public boolean viewUncheckedTransactionHistory() {
        System.out.println("Unchecked Transaction History:");
        List<Transaction> uncheckedTransactions = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if ("Unchecked".equals(transaction.getState())) {
                uncheckedTransactions.add(transaction);
            }
        }
        if (uncheckedTransactions.isEmpty()) {
            System.out.println("No unchecked transactions found.");
            return false;
        } else {
            for (Transaction transaction : uncheckedTransactions) {
                System.out.println(transaction);
            }
            return true;
        }
    }

    /**
     * Loads the transaction history from the CSV file.
     *
     * @return the transaction history
     */
    private List<Transaction> loadTransactionHistory() {
        List<Transaction> history = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(transactionHistoryCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String transactionId = data[0];
                double amount = Double.parseDouble(data[1]);
                LocalDateTime timestamp = LocalDateTime.parse(data[2]);
                String type = data[3];
                String source = data[4];
                String destination = data[5];
                String state = data[6];
                if ("TD".equals(type)) {
                    LocalDate due = LocalDate.parse(data[7]);
                    double profitRate = Double.parseDouble(data[8]);
                    int months = Integer.parseInt(data[9]);
                    history.add(new TermDeposit(transactionId, amount, timestamp, type, source, destination, state, due, profitRate, months));
                } else {
                    history.add(new Transaction(transactionId, amount, timestamp, type, source, destination, state));
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error loading transaction history from CSV: " + e.getMessage());
        }
        return history;
    }

    /**
     * Loads the unchecked transaction history.
     *
     * @return the unchecked transaction history
     */
    public List<Transaction> loadUncheckedTransHistory() {
        List<Transaction> uncheckedTransactions = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if ("Unchecked".equals(transaction.getState())) {
                uncheckedTransactions.add(transaction);
            }
        }
        if (uncheckedTransactions.isEmpty()) {
            System.out.println("No unchecked transactions found.");
        }
        return uncheckedTransactions;
    }

    /**
     * Saves the transaction history to the CSV file.
     */
    public void saveTransactionHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionHistoryCSV))) {
            for (Transaction transaction : transactionHistory) {
                if (transaction instanceof TermDeposit) {
                    bw.write(TDtoCSV((TermDeposit) transaction));
                } else {
                    bw.write(transactionToCSV(transaction));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction history to CSV: " + e.getMessage());
        }
    }

    /**
     * Converts a transaction to a CSV string.
     *
     * @param transaction the transaction to convert
     * @return the CSV string
     */
    private String transactionToCSV(Transaction transaction) {
        return String.join(",", transaction.getTransactionId(),
                String.valueOf(transaction.getAmount()),
                transaction.getTimestamp().toString(),
                transaction.getType(),
                transaction.getSource(),
                transaction.getDestination(),
                transaction.getState());
    }

    /**
     * Converts a term deposit to a CSV string.
     *
     * @param TD the term deposit to convert
     * @return the CSV string
     */
    private String TDtoCSV(TermDeposit TD) {
        return String.join(",", TD.getTransactionId(),
                String.valueOf(TD.getAmount()),
                TD.getTimestamp().toString(),
                TD.getType(),
                TD.getSource(),
                TD.getDestination(),
                TD.getState(),
                TD.getDue().toString(),
                String.valueOf(TD.getProfitRate()),
                String.valueOf(TD.getMonths()));
    }

    /**
     * Changes the state of the transaction with the given transactionId to the given newState.
     *
     * @param transactionId the transaction ID
     * @param newState the new state
     * @return true if the state was changed successfully, false otherwise
     */
    public boolean changeTransactionState(String transactionId, String newState) {
        int count = 0;
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionId().contains(transactionId)) {
                count++;
                if (count > 1) {
                    System.out.println("Multiple transactions found with the given ID. Please provide a more specific ID.");
                    return false;
                }
                if ("Confirmed".equals(newState)) {
                    transaction.confirmRequest();
                    updateChildBalance(transaction);
                } else if ("Rejected".equals(newState)) {
                    transaction.rejectRequest();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return false;
                }
                saveTransactionHistory();
            }
        }
        uncheckedTransHistoryUpdate();
        if (count == 0) {
            System.out.println("Transaction ID not found.");
            return false;
        }
        return true;
    }

    /**
     * Updates the balance of the child involved in the given transaction.
     *
     * @param transaction the transaction
     */
    private void updateChildBalance(Transaction transaction) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(transaction.getDestination())) {
                    double currentBalance = Double.parseDouble(parts[2]);
                    double savingGoal = parts.length > 3 ? Double.parseDouble(parts[3]) : 0.0;

                    if ("Deposit".equals(transaction.getType())) {
                        currentBalance += transaction.getAmount();
                    } else if ("Withdrawal".equals(transaction.getType())) {
                        currentBalance -= transaction.getAmount();
                    } else if ("TD".equals(transaction.getType())) {
                        TermDeposit td = (TermDeposit) transaction;
                        int compare = LocalDate.now().compareTo(td.getDue());
                        if (compare >= 0) {
                            currentBalance += (td.getAmount() * td.getProfitRate() * td.getMonths());
                            td.TDmaturity();
                        }
                    }
                    lines.add(parts[0] + "," + parts[1] + "," + currentBalance + "," + savingGoal);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating child balance: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing updated child balance to CSV: " + e.getMessage());
        }
    }

    /**
     * Updates the saving goal of the child with the given childName to the given newSavingGoal.
     *
     * @param childName the child's name
     * @param newSavingGoal the new saving goal
     */
    public void updateChildSavingGoal(String childName, double newSavingGoal) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(childName)) {
                    lines.add(parts[0] + "," + parts[1] + "," + parts[2] + "," + newSavingGoal);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating child saving goal: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(childCSV))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing updated child saving goal to CSV: " + e.getMessage());
        }
    }

    /**
     * Returns the transaction history.
     *
     * @return the transaction history
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public List<Transaction> getMyTransactionHistory(Child child) {
        List<Transaction> myTransaction = new ArrayList<>();
        if (child != null) {
            for (Transaction transaction : transactionHistory) {
                if (child.getUsername().equals(transaction.getSource())
                        || child.getUsername().equals(transaction.getDestination())) {
                    myTransaction.add(transaction);
                }
            }
        } else {
            return getTransactionHistory();
        }

        if (myTransaction.isEmpty()) {
            System.out.println("No Child's Transactions Found.");
        }
        return myTransaction;
    }

    /**
     * Returns the unchecked transaction history.
     *
     * @return the unchecked transaction history
     */
    public List<Transaction> getUncheckedTransHistory() {
        return uncheckedTransHistory;
    }

    /**
     * Updates the unchecked transaction history.
     */
    public void uncheckedTransHistoryUpdate() {
        uncheckedTransHistory = loadUncheckedTransHistory();
    }

    /**
     * Returns the current profit rate.
     *
     * @return the current profit rate
     */
    public double getCurrentProfitRate() {
        double profitRate = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(profitRateCSV))) {
            String line;
            String nextLine;

            if ((line = br.readLine()) != null) {
                nextLine = br.readLine();
                while (nextLine != null) {
                    line = nextLine;
                    nextLine = br.readLine();
                }
                profitRate = Double.parseDouble(line);
                br.close();
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error loading profit rate from CSV: " + e.getMessage());
        }
        return profitRate;
    }

    /**
     * Sets the profit rate to the given newRate.
     *
     * @param newRate the new rate
     * @return true if the rate was set successfully, false otherwise
     */
    public boolean setProfitRate(double newRate) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(profitRateCSV, true))) {
            bw.newLine();
            bw.write(String.valueOf(newRate));
        } catch (IOException e) {
            System.err.println("Error saving TD rate to CSV: " + e.getMessage());
            return false;
        }
        return true;
    }
}
