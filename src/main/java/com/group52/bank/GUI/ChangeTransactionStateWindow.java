package com.group52.bank.GUI;

import com.group52.bank.model.Transaction;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChangeTransactionStateWindow extends JFrame {

    private TransactionSystem transSystem;
    private TransactionMenuWindow transactionMenuWindow;

    private JLabel titleLabel;
    private JLabel transactiontitle;
    private JLabel transactionIdLabel;
    private JTextField transactionIdField;
    private JLabel newStateLabel;
    private JComboBox<String> newStateComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private JTable uncheckedTransTable;
    private JScrollPane scrollPane;

    public ChangeTransactionStateWindow(TransactionSystem transSystem, TransactionMenuWindow transactionMenuWindow) {
        super("Change Transaction State");
        this.transSystem = transSystem;
        this.transactionMenuWindow = transactionMenuWindow;

        // Set layout manager for the frame
        setLayout(new GridLayout(4, 2));

        transactiontitle = new JLabel("Unchecked Transaction List");
        add(transactiontitle);
        uncheckedTransTable = createUncheckedTransTable();
        scrollPane = new JScrollPane(uncheckedTransTable);
//        scrollPane.setSize(new Dimension(1, 1));
        add(scrollPane);

        transactionIdLabel = new JLabel("Transaction ID:");
        add(transactionIdLabel);
        transactionIdField = new JTextField(15);
        add(transactionIdField);

        newStateLabel = new JLabel("New State:");
        add(newStateLabel);
        newStateComboBox = new JComboBox<>(new String[]{"Confirmed", "Rejected"});
        add(newStateComboBox);

        submitButton = new JButton("Submit");
        add(submitButton);
        submitButton.addActionListener(e -> handleChangeState());

        cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void handleChangeState() {
        String transactionId = transactionIdField.getText();
        String newState = (String) newStateComboBox.getSelectedItem();

        if (transSystem.changeTransactionState(transactionId, newState)) {
            JOptionPane.showMessageDialog(this, "Transaction state changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close window on success
            // transactionMenuWindow.refreshTransactionHistory(); // Optional: Update transaction list in parent menu
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change transaction state. Transaction ID not found or invalid state.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTable createUncheckedTransTable() {
        List<Transaction> transactions = transSystem.getUncheckedTransHistory();
        if (transactions.isEmpty()) {
            return new JTable(new DefaultTableModel(new Object[][]{{"No unchecked transactions found."}}, new String[]{"Message"}));
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
