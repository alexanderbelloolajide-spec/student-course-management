/**
 * Course.java
 * Represents a single course with code, title, and credit units.
 * This class demonstrates encapsulation with private attributes and public accessors.
 */
public class Course {
    // Private attributes for encapsulation
    private String courseCode;
    private String courseTitle;
    private int unit;

    /**
     * Default constructor - initializes course with empty values
     */
    public Course() {
        this.courseCode = "";
        this.courseTitle = "";
        this.unit = 0;
    }

    /**
     * Parameterized constructor - initializes course with provided values
     * @param courseCode the unique course code
     * @param courseTitle the title of the course
     * @param unit the number of credit units
     */
    public Course(String courseCode, String courseTitle, int unit) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.unit = unit;
    }

    /**
     * Getter for course code
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Setter for course code
     * @param courseCode the course code to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Getter for course title
     * @return the course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Setter for course title
     * @param courseTitle the course title to set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Getter for credit units
     * @return the number of credit units
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Setter for credit units
     * @param unit the number of credit units to set
     */
    public void setUnit(int unit) {
        this.unit = unit;
    }

    /**
     * Overridden toString() method for displaying course information
     * @return formatted string representation of the course
     */
    @Override
    public String toString() {
        return String.format("%-10s | %-25s | %d", courseCode, courseTitle, unit);
    }
}
