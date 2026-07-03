import java.util.Random;

public class NARORainfallAnalyzer {
    public static void main(String[] args) {
        // (i) Generate 30 random daily rainfall readings between 0 and 60 mm
        Random random = new Random();
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