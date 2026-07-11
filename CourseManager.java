import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseManager class manages the collection of courses
 * Handles add, search, compute total units, and file I/O operations
 */
public class CourseManager {
    private List<Course> courses;

    /**
     * Constructor initializes the courses list
     */
    public CourseManager() {
        this.courses = new ArrayList<>();
    }

    /**
     * Add a new course to the list
     * @param course the course to add
     * @return true if course added successfully
     */
    public boolean addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        // Validate input
        if (course.getCourseCode().trim().isEmpty() || 
            course.getCourseTitle().trim().isEmpty() || 
            course.getUnits() <= 0) {
            System.out.println("Invalid course details. Code, title, and units (>0) are required.");
            return false;
        }
        // Check for duplicate course code
        if (searchCourseByCode(course.getCourseCode()) != null) {
            System.out.println("Course with code " + course.getCourseCode() + " already exists.");
            return false;
        }
        courses.add(course);
        return true;
    }

    /**
     * View all courses in the list
     */
    public void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("\n--- No courses recorded yet ---");
            return;
        }
        System.out.println("\n--- All Recorded Courses ---");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
    }

    /**
     * Search for a course by its code (Recursive approach)
     * @param courseCode the code to search for
     * @return the course if found, null otherwise
     */
    public Course searchCourseByCode(String courseCode) {
        return searchCourseByCodeRecursive(courseCode, 0);
    }

    /**
     * Recursive helper method to search for a course by code
     * @param courseCode the code to search for
     * @param index current index in the list
     * @return the course if found, null otherwise
     */
    private Course searchCourseByCodeRecursive(String courseCode, int index) {
        // Base case: if index exceeds list size, course not found
        if (index >= courses.size()) {
            return null;
        }
        // Check if current course matches
        if (courses.get(index).getCourseCode().equalsIgnoreCase(courseCode)) {
            return courses.get(index);
        }
        // Recursive call with next index
        return searchCourseByCodeRecursive(courseCode, index + 1);
    }

    /**
     * Compute and display total units for all courses
     */
    public int computeTotalUnits() {
        int total = 0;
        for (Course course : courses) {
            total += course.getUnits();
        }
        return total;
    }

    /**
     * Save all courses to a file using serialization
     * @param filename the file path to save to
     * @return true if save was successful
     */
    public boolean saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Create a serializable list
            ArrayList<String> courseData = new ArrayList<>();
            for (Course course : courses) {
                courseData.add(course.getCourseCode() + "," + 
                               course.getCourseTitle() + "," + 
                               course.getUnits());
            }
            oos.writeObject(courseData);
            System.out.println("Courses saved to file: " + filename);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load courses from a file
     * @param filename the file path to load from
     * @return true if load was successful
     */
    public boolean loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            ArrayList<String> courseData = (ArrayList<String>) ois.readObject();
            courses.clear();
            for (String data : courseData) {
                String[] parts = data.split(",");
                if (parts.length == 3) {
                    String courseCode = parts[0].trim();
                    String courseTitle = parts[1].trim();
                    int units = Integer.parseInt(parts[2].trim());
                    courses.add(new Course(courseCode, courseTitle, units));
                }
            }
            System.out.println("Courses loaded from file: " + filename);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the list of all courses
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Remove a course by code
     * @param courseCode the code of course to remove
     * @return true if removed successfully
     */
    public boolean removeCourse(String courseCode) {
        Course course = searchCourseByCode(courseCode);
        if (course != null) {
            courses.remove(course);
            System.out.println("Course removed: " + courseCode);
            return true;
        }
        System.out.println("Course not found: " + courseCode);
        return false;
    }
}
