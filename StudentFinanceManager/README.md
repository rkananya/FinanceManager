

---

# Finance Manager

A simple, effective personal finance manager application that helps users track their income, expenses, and savings goals. This program allows users to set a target savings amount, record their expenses by category, and monitor their progress towards their financial goals.

## Features

- **Track Income and Expenses:** Set your income and record expenses across multiple categories.
- **Savings Target:** Set a savings target and track how close you are to achieving it.
- **Spending Alerts:** Receive alerts when you've spent 90% of your target savings amount.
- **Expense Breakdown:** View expenses by category for detailed insights into spending habits.
- **Automatic Calculations:** Automatically calculate the percentage saved based on income and expenses.
- **Error Handling:** Ensure expenses never exceed available funds with error checks.

## Installation

To run the Finance Manager application, follow these steps:

1. Clone the repository:

   git clone https://github.com/rkananya/FinanceManager.git

2. Compile and run the Java files. Ensure you have a working Java environment set up.

## Usage

### Set Income

To begin using the finance manager, you need to set your income:


Expense.setIncome(5000.00);  // Set income to $5000


### Set Savings Target

Set your target savings goal that you'd like to achieve:


Expense expenseTracker = new Expense();
expenseTracker.setTarget(1000.00);  // Target to save $1000


### Add an Expense

You can add expenses by providing a description, category, and the amount spent:


expenseTracker.addExpense("Grocery shopping", "Food", 50.00);
expenseTracker.addExpense("Movie tickets", "Entertainment", 30.00);


### View Current Savings Progress

You can view how much you've saved by calculating the percentage saved based on your total income:


double percentSaved = expenseTracker.calculatePercentSaved();
System.out.println("You have saved " + percentSaved + "% of your income.");


### Check for Spending Alerts

The system will automatically notify you when you have spent 90% of your savings target:


expenseTracker.addExpense("New shoes", "Clothing", 400.00);
// Output: Warning: You have spent 90% of your target savings amount!


### View Expenses by Category

You can view the breakdown of your expenses by category:


Map<String, Double> expensesByCategory = expenseTracker.getExpenseData();
System.out.println(expensesByCategory);


### Sample Output
INITIAL SETUP:

![Screenshot 2025-03-08 004233](https://github.com/user-attachments/assets/9dfa41a4-87fb-4a1c-847f-0c1020b0e76d)

ADD INCOME AND TARGET SAVINGS:

![Screenshot 2025-03-08 004250](https://github.com/user-attachments/assets/59456027-7a3b-4ff4-8fef-114e6ee9968b)

INCOME ADDED

![Screenshot 2025-03-08 004301](https://github.com/user-attachments/assets/6d44ec66-4077-4204-89ec-a245db39dadb)

ADD EXPENSE WITH CATEGORY

![Screenshot 2025-03-08 004337](https://github.com/user-attachments/assets/2fe474bf-be54-4e4a-8a1a-2dff8d32d013)

VIEW EXPENSE

![Screenshot 2025-03-08 004348](https://github.com/user-attachments/assets/94ce49ae-c3f6-44f6-8cb5-576d2a269fba)

SEE CHART OF ANALYSIS
![Screenshot 2025-03-08 004357](https://github.com/user-attachments/assets/2d26fb84-21c9-481d-9061-816096665a30)

GET WARNINGS :

![Screenshot 2025-03-08 004523](https://github.com/user-attachments/assets/c229483d-afc7-4eb8-94b8-7fed75c79ea0)

VARIOUS CATEGORIES:

![Screenshot 2025-03-08 004536](https://github.com/user-attachments/assets/965ff90f-3ce5-4ad3-849e-02d57c49163d)


## Future Features

Some possible features to add in the future:
- **Expense Reports:** Generate monthly or annual reports for easy tracking.
- **Budget Planner:** Allow users to plan budgets for various categories.
- **Graphs & Charts:** Visualize savings progress and spending trends.
- **Integration to DB(Oracle)** This alows scalability and persistance of data throughout sessions
- This is likely to become a part of a project that aims to provide complete tracker and planner for students
- This can include health, study along with finance manager.

## Contributing

Feel free to contribute to this project by forking the repository, making changes, and submitting a pull request.

