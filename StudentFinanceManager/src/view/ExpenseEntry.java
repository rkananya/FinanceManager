package view;

import java.time.LocalDate;

public class ExpenseEntry {
    private LocalDate date; // Date of the expense
    private String description; // Description of the expense
    private double amount; // Amount spent
    private String category; // Category of the expense

    // Constructor
    public ExpenseEntry(LocalDate date, String description, double amount, String category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    // Getter methods
    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    // Overriding toString method for easy printing
    @Override
    public String toString() {
        return "Date: " + date + 
               ", Description: " + description + 
               ", Amount: $" + amount + 
               ", Category: " + category;
    }
}
