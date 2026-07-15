import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**
 * StudentCourseManagement.java
 * Main application class that manages all course operations.
 * Demonstrates use of ArrayList, recursion, file handling, and exception handling.
 */
public class StudentCourseManagement {
    // Class attributes
    private static ArrayList<Course> courses;
    private static Scanner scanner;
    private static final String FILE_NAME = "courses.txt";

    /**
     * Constructor - initializes ArrayList and Scanner
     */
    public StudentCourseManagement() {
        courses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Main method - entry point of the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        StudentCourseManagement app = new StudentCourseManagement();
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Student Course Management System - COS 201 Programming I   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Start recursive menu
        app.showMenu();
        
        // Close resources
        scanner.close();
        System.out.println("\nThank you for using Student Course Management System!");
    }

    /**
     * RECURSIVE METHOD: Displays menu and processes user choices
     * Uses recursion instead of while loop to meet requirement.
     * After each operation (except Exit), calls itself recursively.
     */
    public void showMenu() {
        try {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║         MAIN MENU              ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ 1. Add Course                  ║");
            System.out.println("║ 2. View All Courses            ║");
            System.out.println("║ 3. Search Course by Code       ║");
            System.out.println("║ 4. Compute Total Units         ║");
            System.out.println("║ 5. Save Courses to File        ║");
            System.out.println("║ 6. Load Courses from File      ║");
            System.out.println("║ 7. Exit                        ║");
            System.out.println("╚════════════════════════════════╝");

            System.out.print("\nEnter your choice (1-7): ");
            
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCourse();
                    showMenu(); // Recursive call
                    break;
                case 2:
                    viewCourses();
                    showMenu(); // Recursive call
                    break;
                case 3:
                    searchCourse();
                    showMenu(); // Recursive call
                    break;
                case 4:
                    computeTotalUnits();
                    showMenu(); // Recursive call
                    break;
                case 5:
                    saveToFile();
                    showMenu(); // Recursive call
                    break;
                case 6:
                    loadFromFile();
                    showMenu(); // Recursive call
                    break;
                case 7:
                    System.out.println("\n✓ Exiting application...");
                    return; // Exit recursion
                default:
                    System.out.println("✗ Invalid choice! Please select between 1 and 7.");
                    showMenu(); // Recursive call
            }
        } catch (Exception e) {
            System.out.println("✗ An unexpected error occurred: " + e.getMessage());
            showMenu(); // Recursive call
        }
    }

    /**
     * Adds a new course to the ArrayList
     * Prompts for course code, title, and units
     * Validates input and prevents duplicate course codes
     */
    private void addCourse() {
        try {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║         ADD NEW COURSE         ║");
            System.out.println("╚════════════════════════════════╝");

            // Get course code
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();

            // Validate course code
            if (courseCode.isEmpty()) {
                System.out.println("✗ Course code cannot be empty!");
                return;
            }

            // Check for duplicate course code
            if (isCourseCodeExists(courseCode)) {
                System.out.println("✗ Course code '" + courseCode + "' already exists!");
                return;
            }

            // Get course title
            System.out.print("Enter Course Title: ");
            String courseTitle = scanner.nextLine().trim();

            if (courseTitle.isEmpty()) {
                System.out.println("✗ Course title cannot be empty!");
                return;
            }

            // Get credit units
            System.out.print("Enter Credit Units: ");
            int unit = getIntInput();

            if (unit <= 0) {
                System.out.println("✗ Credit units must be greater than zero!");
                return;
            }

            // Create and add course
            Course course = new Course(courseCode, courseTitle, unit);
            courses.add(course);
            System.out.println("✓ Course added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input format! " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error adding course: " + e.getMessage());
        }
    }

    /**
     * Displays all courses in a formatted table
     * Shows appropriate message if no courses are available
     */
    private void viewCourses() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ALL REGISTERED COURSES                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");

        if (courses.isEmpty()) {
            System.out.println("║ No courses registered yet.                                 ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            return;
        }

        System.out.println("║ Course Code | Course Title              | Units             ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.printf("║ %s              │ %d                 ║\n", 
                course.toString(), i + 1);
        }

        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    /**
     * Searches for a course using course code
     * Search is case-insensitive
     * Displays course details if found, otherwise shows "Course not found"
     */
    private void searchCourse() {
        try {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║      SEARCH COURSE BY CODE     ║");
            System.out.println("╚════════════════════════════════╝");

            System.out.print("Enter Course Code to search: ");
            String searchCode = scanner.nextLine().trim().toUpperCase();

            if (searchCode.isEmpty()) {
                System.out.println("✗ Course code cannot be empty!");
                return;
            }

            // Search for course
            Course foundCourse = null;
            for (Course course : courses) {
                if (course.getCourseCode().equalsIgnoreCase(searchCode)) {
                    foundCourse = course;
                    break;
                }
            }

            if (foundCourse != null) {
                System.out.println("\n✓ Course found!");
                System.out.println("╔════════════════════════════════╗");
                System.out.printf("║ Code:  %s\n", foundCourse.getCourseCode());
                System.out.printf("║ Title: %s\n", foundCourse.getCourseTitle());
                System.out.printf("║ Units: %d\n", foundCourse.getUnit());
                System.out.println("╚════════════════════════════════╝");
            } else {
                System.out.println("✗ Course not found!");
            }

        } catch (Exception e) {
            System.out.println("✗ Error searching course: " + e.getMessage());
        }
    }

    /**
     * Calculates and displays total credit units
     * Iterates through ArrayList to sum all units
     */
    private void computeTotalUnits() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║      TOTAL CREDIT UNITS        ║");
        System.out.println("╚════════════════════════════════╝");

        if (courses.isEmpty()) {
            System.out.println("No courses registered. Total units: 0");
            return;
        }

        int totalUnits = 0;
        for (Course course : courses) {
            totalUnits += course.getUnit();
        }

        System.out.println("Total Credit Units: " + totalUnits);
    }

    /**
     * Saves all courses to a file named courses.txt
     * Format: CourseCode,CourseTitle,Units
     * Uses BufferedWriter for efficient writing
     * Handles IOException properly
     */
    private void saveToFile() {
        try {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║    SAVE COURSES TO FILE        ║");
            System.out.println("╚════════════════════════════════╝");

            if (courses.isEmpty()) {
                System.out.println("✗ No courses to save!");
                return;
            }

            // Use BufferedWriter for efficient file writing
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Course course : courses) {
                    String line = course.getCourseCode() + "," + 
                                  course.getCourseTitle() + "," + 
                                  course.getUnit();
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("✓ Courses saved successfully to '" + FILE_NAME + "'");
                System.out.println("  Total courses saved: " + courses.size());
            } catch (IOException e) {
                System.out.println("✗ IOException: Failed to save file - " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("✗ Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Loads courses from file named courses.txt
     * Format: CourseCode,CourseTitle,Units
     * Clears existing ArrayList before loading
     * Uses BufferedReader for efficient file reading
     * Handles missing files and IOException properly
     */
    private void loadFromFile() {
        try {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║    LOAD COURSES FROM FILE      ║");
            System.out.println("╚═══��════════════════════════════╝");

            File file = new File(FILE_NAME);

            // Check if file exists
            if (!file.exists()) {
                System.out.println("✗ File not found: '" + FILE_NAME + "'");
                return;
            }

            // Clear existing courses
            courses.clear();

            // Use BufferedReader for efficient file reading
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                int loadedCount = 0;

                while ((line = reader.readLine()) != null) {
                    try {
                        String[] parts = line.split(",");
                        
                        if (parts.length != 3) {
                            System.out.println("⚠ Skipping invalid line: " + line);
                            continue;
                        }

                        String courseCode = parts[0].trim();
                        String courseTitle = parts[1].trim();
                        int unit = Integer.parseInt(parts[2].trim());

                        if (courseCode.isEmpty() || courseTitle.isEmpty() || unit <= 0) {
                            System.out.println("⚠ Skipping invalid course data");
                            continue;
                        }

                        Course course = new Course(courseCode, courseTitle, unit);
                        courses.add(course);
                        loadedCount++;

                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Skipping line with invalid unit value: " + line);
                    }
                }

                if (loadedCount > 0) {
                    System.out.println("✓ Courses loaded successfully from '" + FILE_NAME + "'");
                    System.out.println("  Total courses loaded: " + loadedCount);
                } else {
                    System.out.println("✗ No valid courses found in file");
                }

            } catch (FileNotFoundException e) {
                System.out.println("✗ FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("✗ IOException: Failed to read file - " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("✗ Error loading from file: " + e.getMessage());
        }
    }

    /**
     * Helper method: Checks if a course code already exists
     * Case-insensitive comparison
     * @param courseCode the code to check
     * @return true if exists, false otherwise
     */
    private boolean isCourseCodeExists(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method: Gets integer input from user
     * Handles InputMismatchException for invalid input
     * @return the integer value entered by user
     */
    private int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter a valid number.");
            System.out.print("Enter a valid number: ");
            return getIntInput(); // Recursive call for valid input
        } catch (InputMismatchException e) {
            System.out.println("✗ InputMismatchException: " + e.getMessage());
            scanner.nextLine(); // Clear buffer
            System.out.print("Enter a valid number: ");
            return getIntInput(); // Recursive call
        }
    }
}
