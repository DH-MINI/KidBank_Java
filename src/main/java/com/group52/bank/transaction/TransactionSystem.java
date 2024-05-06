package com.group52.bank.transaction;

import com.group52.bank.model.TermDeposit;
import com.group52.bank.model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionSystem {

    private final String transactionHistoryCSV;
    private final String childCSV;

    private final String profitRateCSV;
    private final List<Transaction> transactionHistory;
    private List<Transaction> uncheckedTransHistory;
  
    public TransactionSystem(String transactionHistoryCSV, String childCSV, String profitRateCSV) {
        this.transactionHistoryCSV = transactionHistoryCSV;
        this.childCSV = childCSV;
        this.profitRateCSV = profitRateCSV;
        this.transactionHistory = loadTransactionHistory();
        this.uncheckedTransHistory = loadUncheckedTransHistory();
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        if("Unchecked".equals(transaction.getState())) {
            uncheckedTransHistory.add(transaction);
        }
        saveTransactionHistory();
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.toString());
        }
    }

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
                if(type.equals("TD")){
                    LocalDate due = LocalDate.parse(data[7]);
                    double profitRate = Double.parseDouble(data[8]);
                    int months = Integer.parseInt(data[9]);
                    history.add(new TermDeposit(transactionId, amount, timestamp, type, source, destination, state, due, profitRate, months));
                }
                else{
                    history.add(new Transaction(transactionId, amount, timestamp, type, source, destination, state));
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error loading transaction history from CSV: " + e.getMessage());
        }
        return history;
    }

    public List<Transaction> loadUncheckedTransHistory(){
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

    public void saveTransactionHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionHistoryCSV))) {
            for (Transaction transaction : transactionHistory) {
                if(transaction instanceof TermDeposit){
                    bw.write(TDtoCSV((TermDeposit)transaction));
                }
                else{
                    bw.write(transactionToCSV(transaction));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction history to CSV: " + e.getMessage());
        }
    }

    private String transactionToCSV(Transaction transaction) {
        return String.join(",", transaction.getTransactionId(),
                String.valueOf(transaction.getAmount()),
                transaction.getTimestamp().toString(),
                transaction.getType(),
                transaction.getSource(),
                transaction.getDestination(),
                transaction.getState());
    }

    private String TDtoCSV(TermDeposit TD){
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
    private void updateChildBalance(Transaction transaction) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(childCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(transaction.getDestination())) {
                    double currentBalance = Double.parseDouble(parts[2]);
                    if ("Deposit".equals(transaction.getType())) {
                        currentBalance += transaction.getAmount();
                    } else if ("Withdrawal".equals(transaction.getType())) {
                        currentBalance -= transaction.getAmount();
                    } else if ("TD".equals(transaction.getType())) {
                        TermDeposit td = (TermDeposit) transaction;
                        int compare = LocalDate.now().compareTo(td.getDue());
                        if(compare < 0){
                            currentBalance += (td.getAmount()*td.getProfitRate()*td.getMonths());
                            td.TDmaturity();
                        }
                    }
                    lines.add(parts[0] + "," + parts[1] + "," + currentBalance);
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

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public List<Transaction> getUncheckedTransHistory(){return uncheckedTransHistory; }

    public void uncheckedTransHistoryUpdate(){uncheckedTransHistory = loadUncheckedTransHistory(); }


    public double getCurrentProfitRate(){
        double profitRate = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(profitRateCSV))) {
            String line;
            String nextLine;

            if ((line = br.readLine()) != null) {
                nextLine = br.readLine();
                while (nextLine != null){
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

    public boolean setProfitRate(double newRate){
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
