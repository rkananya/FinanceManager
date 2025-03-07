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
    private List<ExpenseEntry> expenses;
    private double target;

    private static List<Expense> expenseList = new ArrayList<>();
    private Map<String, Double> expensesByCategory = new HashMap<>();

    public Expense() {
        this.description = "";
        this.amountSpent = 0.0; 
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
        return expenses.stream().mapToDouble(e -> e.getAmount()).sum(); // Total expenses 
    }

    public double calculatePercentSaved() {
        if (income <= 0) return 0; 
        return (1 - (getTotalExpenses() / income)) * 100; 
    }

    public double getTarget() {
        return target;
    }

    public boolean isNearSpendingLimit() {
        if (target <= 0) return false; 
        double spentPercentage = (getIncome()-target) * 0.9;
        return getTotalExpenses()>=spentPercentage;
    }


    public static void setIncome(double income) {
        Expense.income += income;
        Expense.currentAmt += income; 
    }

    public static double getIncome() {
        return income;
    }

    public void setTarget(double targetAmount) {
        this.target = targetAmount;
    }

    public void addExpense(String description, String category, double amountSpent) {
        if (amountSpent > currentAmt) {
            throw new IllegalArgumentException("Amount spent cannot be greater than current amount");
        }

        currentAmt -= amountSpent; 
        checkSavingsProgress(); 
        ExpenseEntry newEntry = new ExpenseEntry(LocalDate.now(), description, amountSpent, category);
        expenses.add(newEntry);
        expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amountSpent);

        System.out.println("Expense added: " + newEntry);
    }


    public Map<String, Double> getExpenseData() {

        return new HashMap<>(expensesByCategory);
    }

}
