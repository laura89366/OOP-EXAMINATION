// Custom checked exception
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(double amount, double balance) {
        super("Insufficient funds. Requested: UGX " + amount + ", Available: UGX " + balance);
    }
}

// Method that uses it
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.println("Withdrawal successful. New balance: UGX " + balance);
    }
}
