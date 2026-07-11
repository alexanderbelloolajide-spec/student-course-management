import java.util.Scanner;

/**
 * Main application class for Student Course Management System
 * Provides a console-based menu interface for managing courses
 */
public class StudentCourseManagementSystem {
    private CourseManager courseManager;
    private Scanner scanner;
    private static final String SAVE_FILE = "courses.dat";

    /**
     * Constructor initializes the course manager and scanner
     */
    public StudentCourseManagementSystem() {
        this.courseManager = new CourseManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main menu loop - displays menu and handles user input
     */
    public void run() {
        System.out.println("================================");
        System.out.println("Student Course Management System");
        System.out.println("================================\n");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice (1-7): ");
            
            try {
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        viewAllCourses();
                        break;
                    case 3:
                        searchCourseByCode();
                        break;
                    case 4:
                        computeTotalUnits();
                        break;
                    case 5:
                        saveToFile();
                        break;
                    case 6:
                        loadFromFile();
                        break;
                    case 7:
                        running = false;
                        exitProgram();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.\n");
            }
        }
    }

    /**
     * Display the main menu options
     */
    private void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
    }

    /**
     * Handle adding a new course
     */
    private void addCourse() {
        try {
            System.out.println("\n--- Add Course ---");
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine().trim();
            
            if (courseCode.isEmpty()) {
                System.out.println("Course code cannot be empty.");
                return;
            }

            System.out.print("Enter course title: ");
            String courseTitle = scanner.nextLine().trim();
            
            if (courseTitle.isEmpty()) {
                System.out.println("Course title cannot be empty.");
                return;
            }

            int units = getIntInput("Enter number of units: ");
            
            if (units <= 0) {
                System.out.println("Units must be greater than 0.");
                return;
            }

            Course course = new Course(courseCode, courseTitle, units);
            if (courseManager.addCourse(course)) {
                System.out.println("Course added successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    /**
     * Display all recorded courses
     */
    private void viewAllCourses() {
        courseManager.viewAllCourses();
    }

    /**
     * Search for a course by its code
     */
    private void searchCourseByCode() {
        try {
            System.out.println("\n--- Search Course by Code ---");
            System.out.print("Enter course code to search: ");
            String courseCode = scanner.nextLine().trim();
            
            if (courseCode.isEmpty()) {
                System.out.println("Course code cannot be empty.");
                return;
            }

            Course found = courseManager.searchCourseByCode(courseCode);
            if (found != null) {
                System.out.println("\nCourse found:");
                System.out.println(found);
            } else {
                System.out.println("Course with code '" + courseCode + "' not found.");
            }
        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }

    /**
     * Compute and display total units
     */
    private void computeTotalUnits() {
        int totalUnits = courseManager.computeTotalUnits();
        System.out.println("\n--- Total Units ---");
        if (totalUnits == 0) {
            System.out.println("No courses recorded. Total units: 0");
        } else {
            System.out.println("Total units for all courses: " + totalUnits);
        }
    }

    /**
     * Save courses to file
     */
    private void saveToFile() {
        try {
            System.out.println("\n--- Save to File ---");
            courseManager.saveToFile(SAVE_FILE);
        } catch (Exception e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Load courses from file
     */
    private void loadFromFile() {
        try {
            System.out.println("\n--- Load from File ---");
            courseManager.loadFromFile(SAVE_FILE);
        } catch (Exception e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    /**
     * Exit the program
     */
    private void exitProgram() {
        System.out.println("\n--- Exiting Program ---");
        System.out.println("Thank you for using Student Course Management System!");
        scanner.close();
        System.exit(0);
    }

    /**
     * Helper method to get integer input from user
     * @param prompt the prompt message
     * @return the integer value entered
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return getIntInput(prompt);
        }
    }

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        StudentCourseManagementSystem system = new StudentCourseManagementSystem();
        system.run();
    }
}
