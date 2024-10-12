package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Expense {
    private String description;
    private static double income;
    private double amountSpent; 
    private static double currentAmt;
    private List<ExpenseEntry> expenses; // List of this instance's expenses
    private double target;

    // List to store all expenses across instances
    private static List<Expense> expenseList = new ArrayList<>();
    private Map<String, Double> expensesByCategory = new HashMap<>();

    // Default constructor
    public Expense() {
        this.description = "";
        this.amountSpent = 0.0; // This might not be necessary.
        this.target = 0.0;
        this.expenses = new ArrayList<>();
    }

    private void checkSavingsProgress() {
        if (target <= 0) {
            System.out.println("Target savings not set.");
            return;
        }
        double targetAmount = income - target; // Amount the user can spend
        double spentAmount = income - currentAmt; // Amount already spent

        // Check if the user has spent 90% of the target amount
        if (spentAmount >= targetAmount * 0.9) {
            System.out.println("Warning: You have spent 90% of your target savings amount!");
            System.out.println("Current Balance: $" + currentAmt + ", Target Savings: $" + target);
        }
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(e -> e.getAmount()).sum(); // Total expenses for this instance
    }

    // Method to calculate the percentage saved
    public double calculatePercentSaved() {
        if (income <= 0) return 0; // Avoid division by zero
        return (1 - (getTotalExpenses() / income)) * 100; // Percentage saved
    }

    public double getTarget() {
        return target;
    }

    // Method to check if nearing the spending limit
    public boolean isNearSpendingLimit() {
        if (target <= 0) return false; // Ensure target is set
        double spentPercentage = (getIncome()-target) * 0.9;
        return getTotalExpenses()>=spentPercentage; // Notify when 90% of the target savings is spent
    }

    // Method to set income
    public static void setIncome(double income) {
        Expense.income += income;
        Expense.currentAmt += income; // Assuming this is the initial allocation of income
    }

    public static double getIncome() {
        return income;
    }

    // Method to set target savings
    public void setTarget(double targetAmount) {
        this.target = targetAmount;
    }

    // Method to add an expense
    public void addExpense(String description, String category, double amountSpent) {
        if (amountSpent > currentAmt) {
            throw new IllegalArgumentException("Amount spent cannot be greater than current amount");
        }

        currentAmt -= amountSpent; // Update current available amount
        checkSavingsProgress(); // Check savings progress after spending

        // Create a new ExpenseEntry and add it to the list
        ExpenseEntry newEntry = new ExpenseEntry(LocalDate.now(), description, amountSpent, category);
        expenses.add(newEntry); // Add to the list of expenses

        // Update expenses by category
        expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amountSpent);

        System.out.println("Expense added: " + newEntry);
    }

    // Method to get expense data
    public Map<String, Double> getExpenseData() {
        // Return a copy of the expensesByCategory map to prevent external modification
        return new HashMap<>(expensesByCategory);
    }

}
