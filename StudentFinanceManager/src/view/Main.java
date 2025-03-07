package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Main extends ApplicationFrame {
    private static Expense expenseTracker = new Expense();
    private static DefaultTableModel tableModel;
    private static JLabel balanceLabel;
    private static JLabel percentSavedLabel;
    private static JTextField incomeField;
    private static JTextField expenseField;
    private static JTextField descriptionField;
    private static JTextField targetSavingsField;
    private static JComboBox<String> categoryComboBox;
    private static JPanel cardPanel;
    private static JPanel incomePanel;
    private static JPanel expensePanel;
    private static JPanel tablePanel;
    private static JPanel chartPanel;  
    private static JFreeChart chart;  

    public Main(String title) {
        super(title);
        initUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main("Expense Tracker");
            frame.setSize(600, 700); 
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            RefineryUtilities.centerFrameOnScreen(frame);
        });
    }

    private void initUI() {
    
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.LIGHT_GRAY);
        balanceLabel = new JLabel("Balance: $0.0");
        percentSavedLabel = new JLabel("Percent Saved: 0%");
        statusPanel.add(balanceLabel);
        statusPanel.add(percentSavedLabel);
        add(statusPanel, BorderLayout.NORTH);

      
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.GRAY);
        JButton addIncomeBtn = createStyledButton("Add Income");
        JButton addExpenseBtn = createStyledButton("Add Expense");
        JButton showExpensesBtn = createStyledButton("Show Expenses");
        JButton showChartBtn = createStyledButton("Show Chart");
        buttonPanel.add(addIncomeBtn);
        buttonPanel.add(addExpenseBtn);
        buttonPanel.add(showExpensesBtn);
        buttonPanel.add(showChartBtn);
        add(buttonPanel, BorderLayout.CENTER);
        cardPanel = new JPanel(new CardLayout());

        incomePanel = new JPanel(new GridLayout(3, 2));
        incomeField = new JTextField();
        targetSavingsField = new JTextField();
        JButton confirmIncomeBtn = createStyledButton("Confirm Income");
        incomePanel.add(new JLabel("Enter Income:"));
        incomePanel.add(incomeField);
        incomePanel.add(new JLabel("Target Savings:"));
        incomePanel.add(targetSavingsField);
        incomePanel.add(confirmIncomeBtn);
        confirmIncomeBtn.addActionListener(e -> addIncome());
        expensePanel = new JPanel(new GridLayout(4, 2));
        expenseField = new JTextField();
        descriptionField = new JTextField();
        String[] categories = {"Food", "Miscellaneous", "Health", "Social Life", "Clothing"};
        categoryComboBox = new JComboBox<>(categories);
        JButton confirmExpenseBtn = createStyledButton("Confirm Expense");
        expensePanel.add(new JLabel("Expense Description:"));
        expensePanel.add(descriptionField);
        expensePanel.add(new JLabel("Amount Spent:"));
        expensePanel.add(expenseField);
        expensePanel.add(new JLabel("Category:"));
        expensePanel.add(categoryComboBox);
        expensePanel.add(confirmExpenseBtn);
        confirmExpenseBtn.addActionListener(e -> addExpense());

        tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"Date", "Category", "Description", "Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable expenseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        chartPanel = new JPanel(new BorderLayout());
        updateChart(); 


        cardPanel.add(incomePanel, "Income");
        cardPanel.add(expensePanel, "Expense");
        cardPanel.add(tablePanel, "Table");
        cardPanel.add(chartPanel, "Chart");
        add(cardPanel, BorderLayout.SOUTH);


        addIncomeBtn.addActionListener(e -> showIncomeFields());
        addExpenseBtn.addActionListener(e -> showExpenseFields());
        showExpensesBtn.addActionListener(e -> showExpenseTable());
        showChartBtn.addActionListener(e -> showChart());
    }


    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(120, 30));
        return button;
    }

    private static void showIncomeFields() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Income");
    }


    private static void showExpenseFields() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Expense");
    }


    private static void addIncome() {
        try {
            double income = Double.parseDouble(incomeField.getText());
            double targetSavings = Double.parseDouble(targetSavingsField.getText());

            Expense.setIncome(income);
            expenseTracker.setTarget(targetSavings);
            updateBalanceAndPercent();
            JOptionPane.showMessageDialog(null, "Income Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid amounts.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addExpense() {
        String description = descriptionField.getText();
        double amountSpent;
    
        try {
            amountSpent = Double.parseDouble(expenseField.getText());
            if (Expense.getIncome() <= 0) {
                JOptionPane.showMessageDialog(null, "Please add income first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            expenseTracker.addExpense(description, categoryComboBox.getSelectedItem().toString(), amountSpent);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            tableModel.addRow(new Object[]{sdf.format(date), categoryComboBox.getSelectedItem(), description, "$" + amountSpent});
 
            updateBalanceAndPercent();
    
            descriptionField.setText("");
            expenseField.setText("");
            double totalExpenses = expenseTracker.getTotalExpenses();
            double targetSavings = expenseTracker.getTarget();
    
            if (expenseTracker.isNearSpendingLimit()) {
                JOptionPane.showMessageDialog(null, "Warning: You have reached 90% of your target savings!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            updateChart();
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid expense amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void updateBalanceAndPercent() {
        double balance = Expense.getIncome() - expenseTracker.getTotalExpenses();
        double percentSaved = expenseTracker.calculatePercentSaved();
        balanceLabel.setText("Balance: $" + String.format("%.2f", balance)); 
        percentSavedLabel.setText("Percent Saved: " + String.format("%.2f", percentSaved) + "%");
    }

    private static void updateChart() {
        chartPanel.removeAll();
        JPanel newChartPanel = createDemoPanel();
        chartPanel.add(newChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    private static void showChart() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Chart");
    }

    private static void showExpenseTable() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Table");
    }

    private static DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> expenseData = expenseTracker.getExpenseData();
        for (Map.Entry<String, Double> entry : expenseData.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        return dataset;
    }

    private static JFreeChart createChart(DefaultPieDataset dataset) {
        return ChartFactory.createPieChart(
            "Expenses by Category", dataset, true, true, false
        );
    }

    private static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
}
