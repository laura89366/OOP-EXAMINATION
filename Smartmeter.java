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

