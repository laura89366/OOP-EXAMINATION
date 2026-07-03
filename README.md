# OOP-EXAMINATION
OOP
Relationships, Multiplicity, and Meaning 
Based on the UML diagram, the following relationships are present:
1.	Realization: The Account class implements the Statement interface. This is shown by the dashed line with a hollow triangle arrowhead pointing from Account to Statement. This means Account must provide concrete implementations for all the abstract methods declared in the Statement interface.
2.	Generalization (Inheritance): The SavingsAccount and CurrentAccount classes inherit from the abstract Account class. This is shown by the solid line with a hollow triangle arrowhead pointing from the subclasses to the superclass. This creates an "is-a" relationship: a SavingsAccount is an Account.
3.	Association (Aggregation): There is an aggregation relationship between Customer and Account. Aggregation is a "has-a" relationship where one class (the whole) contains a collection of another class (the part). This is shown by the line with a hollow diamond on the Customer side.
4.	Dependency: The Customer class depends on the Account class because it uses it as a parameter in the addAccount(Account) method and as the type for the accounts list. This is a loose coupling where a change in Account might affect Customer.
Multiplicity between Customer and Account: 
This means:
A Customer can have zero to any number of Account objects (0..*). The customer may have no accounts, one account, or many accounts.
Each Account must be associated with exactly one Customer (1). This means an account cannot exist without a customer, and it cannot be shared between multiple customers. This is a common design for a banking system where each account belongs to a single owner.
(b) Implementation of Statement Interface and Account Class 
java
import java.util.Date;
// --- Statement Interface ---
interface Statement {
    String generateStatement();}
// --- Abstract Account Class ---
abstract class Account implements Statement {
    private int accountNumber;
    protected double balance;
    private Date dateCreated;
    // Constructor
    public Account(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.dateCreated = new Date(); // Sets the current date and time}
    // Concrete methods
    public int getAccountNumber() {
        return accountNumber;}
    public double getBalance() {
        return balance;}
    public Date getDateCreated() {
        return dateCreated;}
    // Abstract methods to be implemented by subclasses
    public abstract void withdraw(double amount);
    public abstract void deposit(double amount);
    // Implement the generateStatement method from the Statement interface
    @Override
    public String generateStatement() {
        return "Account Number: " + accountNumber + "\nCurrent Balance: UGX " + String.format("%.2f", balance);}}
(c) Implementation of SavingsAccount and CurrentAccount 
java
// --- SavingsAccount Class ---
class SavingsAccount extends Account {
    private double interestRate; // e.g., 0.05 for 5%
    public SavingsAccount(int accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;}}
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
        double interest = balance * (interestRate / 100);
        balance += interest;
        System.out.println("Interest of UGX " + String.format("%.2f", interest) + " added at rate " + interestRate + "%.");
    }
}
// --- CurrentAccount Class ---
class CurrentAccount extends Account {
    private double overdraftLimit; // e.g., -1000.00
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
(d) Implementation of Customer Class 
java
import java.util.ArrayList;
class Customer {
    private int customerId;
    private String name;
    private ArrayList<Account> accounts; // Composition/Aggregation
    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new ArrayList<>();
    }
   // Add an account to the customer's list
    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account " + account.getAccountNumber() + " added for customer " + name + ".");
    }
    // Calculate and return the total worth (sum of all account balances)
    public double totalWorth() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", totalWorth=UGX " + String.format("%.2f", totalWorth()) +
                '}';
    }
}
QUESTION TWO 
(a) Output and Explanation 
java
public class FinallyTest {
    public static void main(String[] args) {
        System.out.println(getValue());
    }
    public static int getValue() {
        try {
            return 0;
        } catch (Exception e) {
            return 1;
        } finally {
            return 2;
        }
    }
}
Output:
text
2
Explanation:
The finally block is always executed, regardless of whether an exception is thrown or caught. Even though the try block has a return 0; statement, the finally block is executed before the method returns. Because the finally block contains its own return 2; statement, this return value overrides the one from the try block. The value finally printed by the caller is 2. If the finally block did not have a return, the method would return 0.
(b) Output and Return Values for risky(0) and risky(2) 
java
public static String risky(int x) {
    try {
        if (x == 0) {
            throw new ArithmeticException("Bad zero!");
        }
        System.out.println("No exception");
        return "A";
    } catch (ArithmeticException e) {
        System.out.println("Caught: " + e.getMessage());
        return "B";
    } finally {
        System.out.println("Finally done.");
    }
}
•	Call to risky(0):
Printed: "Caught: Bad zero!" followed by "Finally done."
Returned: "B"
•	Call to risky(2):
Printed: "No exception" followed by "Finally done."
Returned: "A"
(c) Custom Checked Exception and Explanation (8 Marks)
java
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
Difference between Checked and Unchecked Exceptions:
Checked Exceptions: These are exceptions that a compiler forces the programmer to handle. They represent conditions that, while not errors in the program's logic, are beyond the programmer's immediate control and should be anticipated. They must be either caught in a try-catch block or declared in the method signature using the throws keyword. Examples include IOException (file not found) and SQLException (database error).
•	Unchecked Exceptions: These are exceptions that a compiler does not force the programmer to handle. They typically represent programming errors (bugs) that should be fixed, not caught. They are subclasses of RuntimeException. Examples include NullPointerException (accessing an object that is null), ArrayIndexOutOfBoundsException (accessing an invalid array index), and ArithmeticException (division by zero).
(d) Rewriting Snippet with try with resources 
The given snippet leaks a file handle if an exception occurs before reader.close() is called. The try with resources statement automatically closes resources that implement AutoCloseable.
Original Snippet:
java
BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
String line = reader.readLine();
System.out.println(line);
reader.close();
Rewritten Snippet:
java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class TryWithResourcesDemo {
    public static void main(String[] args) {
        // The resource is declared and initialized in the try block.
        // It will be automatically closed at the end of the try block.
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line = reader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            // Handle the exception (e.g., file not found, read error)
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
This ensures that reader.close() is called implicitly, even if reader.readLine() throws an IOException.
SECTION B 
QUESTION THREE 
(a) Key Classes, Responsibilities, and Collaborators 
1.	Class: SmartMeter
Primary Responsibility: Represent a physical smart water meter, track its credit balance and valve status, and manage water consumption.
Collaborator: ConsumptionLog (to record consumption events).
2.	Class: Customer
Primary Responsibility: Represent a customer who owns one or more smart meters and purchases water credit.
Collaborator: SmartMeter (to load tokens).
3.	Class: Token
Primary Responsibility: Represent a purchased water credit (or token) with a unique code and value.
Collaborator: SmartMeter (to load credit into the meter).
4.	Class: ConsumptionEvent
Primary Responsibility: Log a single water usage event, including the amount of water used, the time, and the meter ID.
Collaborator: SmartMeter (to log consumption).
5.	Class: LeakDetector
Primary Responsibility: Monitor consumption patterns from ConsumptionEvent logs and flag a suspected leak if abnormally high continuous flow is detected.
Collaborator: ConsumptionEvent.

(b) Complete SmartMeter Class 
java
public class SmartMeter {
    // i. Private fields
    private String meterId;
    private double creditBalance;
    private boolean valveOpen;
    // ii. Constructor
    public SmartMeter(String meterId, double openingCredit) {
        this.meterId = meterId;
        this.creditBalance = openingCredit;
        this.valveOpen = true; // Initially open
        System.out.println("Meter " + meterId + " initialized with UGX " + openingCredit + ". Valve is OPEN.");
    }
    // iii. loadToken method
    public double loadToken(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid token amount: UGX " + amount);
            return creditBalance;
        }
        creditBalance += amount;
        if (!valveOpen) {
            valveOpen = true;
            System.out.println("Valve re-opened after loading token.");
        }
        System.out.println("Token of UGX " + amount + " loaded. New balance: UGX " + String.format("%.2f", creditBalance));
        return creditBalance;
    }
    // iv. recordConsumption method
    public boolean recordConsumption(double litres) {
        if (litres <= 0) {
            System.out.println("Invalid consumption amount: " + litres + " litres.");
            return false;
        }
        if (!valveOpen || creditBalance <= 0) {
            System.out.println("Meter " + meterId + " is inactive or has insufficient credit.");
            return false;
        }
        double cost = litres * 50; // UGX 50 per litre
        if (creditBalance >= cost) {
            creditBalance -= cost;
            System.out.println("Consumed " + litres + " litres. Cost: UGX " + cost + ". Remaining balance: UGX " + String.format("%.2f", creditBalance));
            return true;
        } else {
            // Insufficient credit to cover the full consumption
            creditBalance = 0;
            valveOpen = false; // Close the valve
            System.out.println("Credit exhausted. Consumption blocked. Valve CLOSED.");
            return false; // Water was not dispensed for this request
        }
    }
    // Getters for testing (optional but good practice)
    public String getMeterId() { return meterId; }
    public double getCreditBalance() { return creditBalance; }
    public boolean isValveOpen() { return valveOpen; }
}
(c) JUnit Tests 
java
import org.junit.jupiter.aSpi.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SmartMeterTest {
    @Test
    void testLoadTokenReopensValve() {
        SmartMeter meter = new SmartMeter("M-100", 10.0);
        // Close the valve manually by consuming all credit
        meter.recordConsumption(0.21); // 0.21 litres = UGX 10.5, more than balance
        assertFalse(meter.isValveOpen());
        assertEquals(0, meter.getCreditBalance());
        // Load a new token
        meter.loadToken(50.0);
        assertTrue(meter.isValveOpen());
        assertEquals(50.0, meter.getCreditBalance());
    }
    @Test
    void testConsumptionBeyondCreditClosesValve() {
        SmartMeter meter = new SmartMeter("M-200", 100.0);
        assertTrue(meter.isValveOpen());
        // Consume 2.5 litres (cost = UGX 125), which exceeds the UGX 100 balance
        boolean dispensed = meter.recordConsumption(2.5);
        assertFalse(dispensed);
        assertEquals(0, meter.getCreditBalance());
        assertFalse(meter.isValveOpen());
    }
}
(d) Explanation of Encapsulation and its Importance 
Encapsulation is applied in the SmartMeter class by declaring all data fields (meterId, creditBalance, valveOpen) as private. This means they cannot be accessed or modified directly from outside the class. Access to and modification of these fields is only possible through the public methods provided, such as loadToken(double amount) and recordConsumption(double litres).
Why it matters for billing integrity at KIS:
1.	Data Integrity: It ensures that the credit balance can only be changed in controlled ways. A customer cannot directly set their own balance to a high value, preventing fraud. The credit balance is only increased via the loadToken method and decreased according to the fixed cost per litre in recordConsumption.
2.	Business Logic Enforcement: Encapsulation allows the SmartMeter class to enforce the business rules of the system. The complex logic of checking if the valve is open, calculating the cost, deducting credit, and closing the valve when credit is exhausted is all hidden within the class. This ensures that these rules are always applied correctly and consistently.
3.	Reduced Errors and Easy Maintenance: By hiding the internal state, the class prevents external code from putting the meter into an inconsistent state (e.g., closing the valve without setting the balance to 0). If the cost per litre changes, the developer only needs to update the logic in the recordConsumption method, and all meters will automatically use the new price without any external code changes.

QUESTION FOUR 
(a) Distinguishing for and while loops 
The main difference is in their intended use and structure.
A for loop is generally used when the number of iterations is known beforehand (definite iteration). It combines the initialization, condition, and increment/decrement in one concise line.
A while loop is generally used when the number of iterations depends on a condition that may change during execution and is not known in advance (indefinite iteration).
Code snippets for recording daily rainfall for a 30-day month:
Using for loop:
java
import java.util.Scanner;
// Assuming the rainfall array is already declared and a Scanner 'input' exists
double[]   dailyRainfall = new double[30];
for (int day = 0; day < dailyRainfall.length; day++) {
    System.out.print("Enter rainfall for day " + (day + 1) + ": ");
    dailyRainfall[day] = input.nextDouble();
}
System.out.println("Finished recording for all 30 days.");
Using while loop:
java
import java.util.Scanner;
// Assuming the rainfall array is already declared and a Scanner 'input' exists
double[] dailyRainfall = new double[30];
int day = 0;
while (day < dailyRainfall.length) {
    System.out.print("Enter rainfall for day " + (day + 1) + ": ");
    dailyRainfall[day] = input.nextDouble();
    day++;
}
System.out.println("Finished recording for all 30 days.");
(b) Single vs Multi-dimensional Arrays for Sensor Data 
Single-dimensional Array: A single-dimensional array could be used to store a single type of data over a period. For example, we could have a double[] dailyRainfall array to store the rainfall for each day of the month in a single location. It is a simple and straightforward way to represent a list of values.
Multi-dimensional Array: A multi-dimensional array (like a 2D array) is more suitable for storing complex or multi-faceted data. For example, if we had 10 sensors across the farm, we could use a 2D array double[][] sensorData = new double[30][10];. The first dimension represents the day (0-29), and the second dimension represents the sensor ID (0-9). This structure allows us to access the data in an organized way: sensorData[day][sensorId]. It can also be extended for multiple attributes, e.g., double[][][] data = new double[30][10][2]; where the third dimension could represent rainfall and soil moisture.
(c) Analysis of Code Output 
java
int[][] matrix = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
for (int i = 0; i < 3; i++) {
    System.out.print(matrix[i][1] + " ");
}
for (int i = 0; i < 3; i++) {
    System.out.print(matrix[1][i] + " ");
}
Analysis:
The first loop iterates i from 0 to 2. For each i, it prints matrix[i][1], which is the element in row i and column 1. This prints: matrix[0][1] (2), matrix[1][1] (5), and matrix[2][1] (8). So the output is 2 5 8.
The second loop is separate and iterates i from 0 to 2. For each i, it prints matrix[1][i], which is the element in row 1 and column i. This prints: matrix[1][0] (4), matrix[1][1] (5), and matrix[1][2] (6). So the output is 4 5 6.
Exact Output:
text
2 5 8 4 5 6 
(d) Complete Java Program 
java
import java.util.Random;
public class NARORainfallAnalyzer {
    public static void main(String[] args) {
        // (i) Generate 30 random daily rainfall readings between 0 and 60 mm
        Random random = new Random ();
        double[] dailyRainfall = new double[30];
        double totalRainfall = 0;
        System.out.println("--- Generated Daily Rainfall Readings (mm) ---");
        for (int day = 0; day < dailyRainfall.length; day++) {
            // Generate a random double between 0.0 and 60.0
            dailyRainfall[day] = random.nextDouble() * 60;
            totalRainfall += dailyRainfall[day];
            System.out.printf("Day %d: %.2f mm\n", day + 1, dailyRainfall[day]);
        }
        // (ii) Compute and display total and average
        double averageRainfall = totalRainfall / dailyRainfall.length;
        System.out.println("\n--- Summary ---");
        System.out.printf("Total Rainfall: %.2f mm\n", totalRainfall);
        System.out.printf("Average Monthly Rainfall: %.2f mm\n", averageRainfall);
        // (iii) Count "wet days" (rainfall > 30 mm)
        int wetDayCount = 0;
        for (double rainfall : dailyRainfall) {
            if (rainfall > 30.0) {
                wetDayCount++;
            }
        }
        System.out.println("Number of 'Wet Days' (> 30 mm): " + wetDayCount);
        // (iv) Classify the month using if-else-if structure
        System.out.print("Month Classification: ");
        if (totalRainfall <= 300) {
            System.out.println("Dry (Total ≤ 300 mm)");
        } else if (totalRainfall >= 600) {
            System.out.println("Flood-risk (Total ≥ 600 mm)");
        } else { // totalRainfall is between 300 and 600
            System.out.println("Normal (300-600 mm)");
        }
    }
}


