package com.group52.bank.GUI;

import com.group52.bank.model.Transaction;
import com.group52.bank.transaction.TransactionSystem;
import com.group52.bank.GUI.setting.CreateNewFont;
import com.group52.bank.GUI.setting.SetImageSize;

import javax.swing.*;
import com.group52.bank.transaction.TransactionSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

/**
 * This class represents a window for changing the state of a transaction.
 */

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
    private ImageIcon backgroundImage = new ImageIcon("src/main/resources/Image/ChildMenuBack.png");
    private ImageIcon LabelImage = new ImageIcon("src/main/resources/Image/Label1.png");
    private JPanel centerPanel;


    /**
     * Constructs a new ChangeTransactionStateWindow with the given transaction system and transaction menu window.
     *
     * @param transSystem the transaction system
     * @param transactionMenuWindow the transaction menu window
     * @throws IOException if there is an error reading the font file
     * @throws FontFormatException if the font file format is not supported
     */
    public ChangeTransactionStateWindow(TransactionSystem transSystem, TransactionMenuWindow transactionMenuWindow) throws IOException, FontFormatException {
        super("Change Transaction State");
        this.transSystem = transSystem;
        this.transactionMenuWindow = transactionMenuWindow;

        // Frame Settings
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        this.setLayout(new BorderLayout(50, 30));

        // Set the TransactionTable
        uncheckedTransTable = createUncheckedTransTable();
        scrollPane = new JScrollPane(uncheckedTransTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 250));
        add(scrollPane, BorderLayout.NORTH);

        // Add a new panel at the center
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2));
        centerPanel.setOpaque(false);

        // Set the TextLabel
        JPanel titleLabelPanel = new JPanel();
        JLabel textLabel = new JLabel();
        textLabel.setLayout(new BorderLayout());
        textLabel.setIcon(new SetImageSize(LabelImage, 0.3).getScaledImage());
        transactionIdLabel = new JLabel("  Confirm  the  Transactions!");
        transactionIdLabel.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        textLabel.add(transactionIdLabel, BorderLayout.CENTER);
        titleLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints LabelConstraints = new GridBagConstraints();
        LabelConstraints.insets = new Insets(50, 100, 50, 50);
        titleLabelPanel.add(textLabel, LabelConstraints);
        titleLabelPanel.setOpaque(false);
        centerPanel.add(titleLabelPanel);

        // Set the text field
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false);
        transactionIdField = new JTextField();
        transactionIdField.setPreferredSize(new Dimension(600,100));
        transactionIdField.setFont(new CreateNewFont("Caveat", 35f).getFont());
        transactionIdField.setToolTipText("Enter  transaction  ID  and  click  'Confirm'");
        transactionIdField.setText("Enter  transaction  ID  and  click  'Confirm'");
        transactionIdField.setForeground(new Color(204, 204, 204));
        transactionIdField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(transactionIdField.getText().equals("Enter  transaction  ID  and  click  'Confirm'")){
                    transactionIdField.setText("");
                    transactionIdField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(transactionIdField.getText().isEmpty()){
                    transactionIdField.setText("Enter  transaction  ID  and  click  'Confirm'");
                    transactionIdField.setForeground(new Color(204, 204, 204));
                }
            }
        });
        // Set constraints of textfield
        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.weightx = 0.0;
//        constraints.weighty = 0.0;
//        constraints.fill = GridBagConstraints.NONE;
//        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(50, 200, 50, 50);
        textPanel.add(transactionIdField, constraints);
        centerPanel.add(textPanel);


        // Set StateLabel
        JLabel stateLabel = new JLabel();
        stateLabel.setLayout(new BorderLayout());
        stateLabel.setIcon(new SetImageSize(LabelImage, 0.3).getScaledImage());
        newStateLabel = new JLabel("       Select  the  New  State");
        newStateLabel.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        stateLabel.add(newStateLabel, BorderLayout.CENTER);
        JPanel stateLabelPanel = new JPanel();
        stateLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints stateLabelConstraints = new GridBagConstraints();
        stateLabelConstraints.insets = new Insets(50, 100, 50, 50);
        stateLabelPanel.add(stateLabel, stateLabelConstraints);
        stateLabelPanel.setOpaque(false);
        centerPanel.add(stateLabelPanel);

        // Set state combo box
        JPanel stateComboPanel = new JPanel();
        stateComboPanel.setLayout(new GridBagLayout());
        GridBagConstraints stateBoxConstraints = new GridBagConstraints();
        stateBoxConstraints.insets = new Insets(50, 200, 50, 50);
        newStateComboBox = new JComboBox<>(new String[]{"Confirmed", "Rejected"});
        newStateComboBox.setFont(new CreateNewFont("PoetsenOne", 38f).getFont());
        newStateComboBox.setPreferredSize(new Dimension(600, 100));
        stateComboPanel.add(newStateComboBox, stateBoxConstraints);
        stateComboPanel.setOpaque(false);
        centerPanel.add(stateComboPanel);

        // Add the CenterPanel
        add(centerPanel, BorderLayout.CENTER);


        //Set Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 50));
        JPanel confirmPanel = new JPanel();
        JPanel cancelPanel = new JPanel();
        confirmPanel.setOpaque(false);
        cancelPanel.setOpaque(false);
        submitButton = createColoredButton("Confirm");
        cancelButton = createColoredButton("Cancel");
        submitButton.setPreferredSize(new Dimension(200, 100));
        cancelButton.setPreferredSize(new Dimension(200, 100));
        confirmPanel.add(submitButton);
        cancelPanel.add(cancelButton);

        submitButton.addActionListener(e -> handleChangeState());
        cancelButton.addActionListener(e -> this.dispose()); // Close window on cancel
        buttonPanel.add(confirmPanel);
        buttonPanel.add(cancelPanel);

        add(buttonPanel, BorderLayout.SOUTH);
        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }
    /**
     * Handles the change of state for a transaction.
     */

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
    /**
     * Creates a table for unchecked transactions.
     *
     * @return the created table
     */
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
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        // Optional: Adjust table column widths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(300);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        // Optional: Adjust table column widths
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBCOLUMNS);

        return table;
    }
    /**
     * Creates a colored button with the given text.
     *
     * @param text the text for the button
     * @return the created button
     * @throws IOException if there is an error reading the font file
     * @throws FontFormatException if the font file format is not supported
     */
    private JButton createColoredButton(String text) throws IOException, FontFormatException {
        JButton button = new JButton(text);
        button.setFont(new CreateNewFont("PermanentMarker", 37f).getFont()); // Decrease font size
        button.setBackground(Color.ORANGE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 30)); // Adjust preferred size for the button
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margin to make the button look less "thick"
        return button;
    }
}
