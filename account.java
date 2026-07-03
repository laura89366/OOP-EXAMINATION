// --- Account Base Class ---
abstract class Account {
    protected int accountNumber;
    protected double balance;

    public Account(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}

// --- SavingsAccount Class ---
class SavingsAccount extends Account {
    private double interestRate; // e.g., 0.05 for 5%

    public SavingsAccount(int accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance - amount >= 0) {
            balance -= amount;
        } else {
            System.out.println("Withdrawal denied. Insufficient funds. Balance: UGX " + String.format("%.2f", balance));
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest of UGX " + String.format("%.2f", interest) + " added at rate " + (interestRate * 100) + "%.");
    }
}

// --- CurrentAccount Class ---
class CurrentAccount extends Account {
    private double overdraftLimit; // e.g., 1000.00

    public CurrentAccount(int accountNumber, double initialBalance, double overdraftLimit) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= -overdraftLimit) {
            balance -= amount;
        } else {
            System.out.println("Withdrawal denied. Overdraft limit of UGX " + String.format("%.2f", overdraftLimit) + " would be exceeded.");
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}