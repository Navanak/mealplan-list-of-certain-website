import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MealPlanDisplay {
    public static void main(String[] args) {
        // Path to the uploaded CSV file
        String csvFilePath = "C:\\Users\\Navan ak\\IdeaProjects\\mealplanlist1\\src\\meal_plan.csv"; // Update the path to your CSV file

        // Map to store meal plans grouped by websites
        Map<String, List<String>> mealPlansByWebsite = new HashMap<>();

        // Load meal plans from the CSV
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            boolean isFirstRow = true; // Skip the header row if it exists
            while ((line = reader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip header
                }

                // Assuming CSV has two columns: Website, MealPlan
                String website = line[0].toLowerCase(); // Normalize to lowercase for case-insensitive comparison
                String mealPlan = line[1];

                // Add meal plan to the corresponding website's list
                mealPlansByWebsite.computeIfAbsent(website, k -> new ArrayList<>()).add(mealPlan);
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        // Take input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the website (e.g., chef's plate, greenchef, homechef, makegoodfood, hellofresh):");
        String userInput = scanner.nextLine().toLowerCase(); // Normalize input to lowercase

        // Search and display the relevant meal plans
        boolean found = false;
        for (Map.Entry<String, List<String>> entry : mealPlansByWebsite.entrySet()) {
            if (entry.getKey().contains(userInput)) {
                System.out.println("\nMeal Plans for " + entry.getKey() + ":");
                for (String mealPlan : entry.getValue()) {
                    System.out.println("- " + mealPlan);
                }
                found = true;
                break;
            }
        }

        // If no match found, notify the user
        if (!found) {
            System.out.println("No meal plans found for the entered website.");
        }

        scanner.close();
    }
}
