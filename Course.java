/**
 * Course class represents a single course with code, title, and units
 */
public class Course {
    private String courseCode;
    private String courseTitle;
    private int units;

    /**
     * Constructor to initialize a Course
     * @param courseCode the unique course code
     * @param courseTitle the title of the course
     * @param units the number of credit units
     */
    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.units = units;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getUnits() {
        return units;
    }

    // Setters
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    /**
     * String representation of the course
     */
    @Override
    public String toString() {
        return String.format("Code: %s | Title: %s | Units: %d", courseCode, courseTitle, units);
    }
}
