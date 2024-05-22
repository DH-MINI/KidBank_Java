package com.group52.bank.GUI;

import com.group52.bank.model.TermDeposit;
import com.group52.bank.model.Transaction;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Change font
        titleLabel.setForeground(Color.BLUE); // Change color
        add(titleLabel, BorderLayout.NORTH);

        transactionTable = createTransactionTable();
        scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Change font
        closeButton.setForeground(Color.RED); // Change text color
        add(closeButton, BorderLayout.SOUTH);
        closeButton.addActionListener(e -> this.dispose()); // Close window on click

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(1500,500);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);

        // Change window background color
        getContentPane().setBackground(Color.LIGHT_GRAY);
    }

    private JTable createTransactionTable() {
        List<Transaction> transactions = transSystem.getTransactionHistory();
        if (transactions.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No transactions found."}}, new String[]{"Message"}));
        }

        // Create column names for the table
        String[] columnNames = {"ID", "Source", "Destination", "Amount", "Date", "State", "Months", "Due", "Profit Rate"};

        // Create a 2D array of transaction data
        String[][] transactionData = new String[transactions.size()][];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            String months = "";
            String due = "";
            String profitRate = "";
            if (transaction instanceof TermDeposit) {
                TermDeposit termDeposit = (TermDeposit) transaction;
                months = String.valueOf(termDeposit.getMonths());
                due = termDeposit.getDue().toString();
                profitRate = String.valueOf(termDeposit.getProfitRate());
            }
            transactionData[i] = new String[]{transaction.getTransactionId(), transaction.getSource(), transaction.getDestination(), "" + transaction.getAmount(), transaction.getTimestamp().toString(), transaction.getState(), months, due, profitRate};
        }

        // Create a JTable model and set the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(transactionData, columnNames);
        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setBorder(new LineBorder(Color.BLUE, 2)); // Add border

        return table;
    }
}