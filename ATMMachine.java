import java.util.ArrayList;
import java.util.Scanner;

public class ATMMachine {
    private int pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public ATMMachine(int initialPin, double initialBalance) {
        this.pin = initialPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    // Method to handle PIN change
    public boolean changePin(int oldPin, int newPin) {
        if (oldPin == this.pin) {
            this.pin = newPin;
            addTransaction("PIN change successful.");
            return true;
        } else {
            addTransaction("PIN change failed. Incorrect old PIN.");
            return false;
        }
    }

    // Method to handle balance inquiry
    public double checkBalance(int pin) {
        if (pin == this.pin) {
            addTransaction("Balance inquiry.");
            return this.balance;
        } else {
            addTransaction("Balance inquiry failed. Incorrect PIN.");
            return -1;
        }
    }

    // Method to handle cash deposit
    public boolean depositCash(int pin, double amount) {
        if (pin == this.pin) {
            if (amount > 0) {
                this.balance += amount;
                addTransaction("Deposited $" + amount);
                return true;
            } else {
                addTransaction("Deposit failed. Invalid amount.");
                return false;
            }
        } else {
            addTransaction("Deposit failed. Incorrect PIN.");
            return false;
        }
    }

    // Method to handle cash withdrawal
    public boolean withdrawCash(int pin, double amount) {
        if (pin == this.pin) {
            if (amount > 0 && amount <= this.balance) {
                this.balance -= amount;
                addTransaction("Withdrew $" + amount);
                return true;
            } else {
                addTransaction("Withdrawal failed. Insufficient balance or invalid amount.");
                return false;
            }
        } else {
            addTransaction("Withdrawal failed. Incorrect PIN.");
            return false;
        }
    }

    // Method to view transaction history
    public ArrayList<String> getTransactionHistory(int pin) {
        if (pin == this.pin) {
            return transactionHistory;
        } else {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add("Access to transaction history denied. Incorrect PIN.");
            return errorList;
        }
    }

    // Helper method to add a transaction to history
    private void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    // Main method to simulate ATM operations
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMMachine atm = new ATMMachine(1234, 10000.00); // Initial PIN and balance

        while (true) {
            System.out.println("Welcome to the ATM Machine");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Cash");
            System.out.println("3. Withdraw Cash");
            System.out.println("4. Change PIN");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Please select an option: ");
            
            int choice = scanner.nextInt();
            int pin;
            double amount;
            boolean success;
            ArrayList<String> history;

            switch (choice) {
                case 1:
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    double balance = atm.checkBalance(pin);
                    if (balance != -1) {
                        System.out.println("Your current balance is $" + balance);
                    } else {
                        System.out.println("Failed to check balance.");
                    }
                    break;

                case 2:
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    System.out.print("Enter amount to deposit: $");
                    amount = scanner.nextDouble();
                    success = atm.depositCash(pin, amount);
                    System.out.println(success ? "Deposit successful." : "Deposit failed.");
                    break;

                case 3:
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextInt();
                    System.out.print("Enter amount to withdraw: $");
                    amount = scanner.nextDouble();
                    success = atm.withdrawCash(pin, amount);
                    System.out.println(success ? "Withdrawal successful." : "Withdrawal failed.");
                    break;

                case 4:
                    System.out.print("Enter old PIN: ");
                    pin = scanner.nextInt();
                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();
                    success = atm.changePin(pin, newPin);
                    System.out.println(success ? "PIN change successful." : "PIN change failed.");
                    break;

                case 5:
                    System.out.print("Enter PIN to view transaction history: ");
                    pin = scanner.nextInt();
                    history = atm.getTransactionHistory(pin);
                    for (String transaction : history) {
                        System.out.println(transaction);
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}