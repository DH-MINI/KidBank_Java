package com.group52.bank.GUI;

import com.group52.bank.model.Transaction;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewTransactionHistoryWindow extends JFrame {

    private TransactionSystem transSystem;

    private JLabel titleLabel;
    private JTable transactionTable;
    private JScrollPane scrollPane;
    private JButton closeButton;

    public ViewTransactionHistoryWindow(TransactionSystem transSystem) {
        super("Transaction History");
        this.transSystem = transSystem;

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Create Swing components
        titleLabel = new JLabel("Transaction History");
        add(titleLabel, BorderLayout.NORTH);

        transactionTable = createTransactionTable();
        scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        closeButton = new JButton("Close");
        add(closeButton, BorderLayout.SOUTH);
        closeButton.addActionListener(e -> this.dispose()); // Close window on click

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private JTable createTransactionTable() {
        List<Transaction> transactions = transSystem.getTransactionHistory();
        if (transactions.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No transactions found."}}, new String[]{"Message"}));
        }

        // Create a 2D array of transaction data
        String[][] transactionData = new String[transactions.size()][];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            transactionData[i] = new String[]{transaction.getTransactionId(), transaction.getSource(), transaction.getDestination(), "" + transaction.getAmount(), transaction.getTimestamp().toString(), transaction.getState()};
        }

        // Create column names for the table
        String[] columnNames = {"ID", "Source", "Destination", "Amount", "Date", "State"};

        // Create a JTable model and set the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(transactionData, columnNames);
        JTable table = new JTable(tableModel);

        // Optional: Adjust table column widths
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBCOLUMNS);

        return table;
    }
}
