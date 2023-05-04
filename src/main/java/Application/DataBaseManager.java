package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBaseManager {

    // Database URL
    protected static final String DB_URL = "jdbc:sqlite:myDB.sqlite";

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS recommendations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "gender TEXT NOT NULL," +
                "target_school TEXT NOT NULL," +
                "current_date TEXT NOT NULL," +
                "program TEXT NOT NULL," +
                "first_semester TEXT NOT NULL," +
                "year INTEGER NOT NULL," +
                "first_course TEXT NOT NULL," +
                "first_course_grade TEXT NOT NULL," +
                "additional_courses TEXT NOT NULL," +
                "additional_courses_grades TEXT NOT NULL," +
                "personal_characteristics TEXT NOT NULL," +
                "academic_characteristics TEXT NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertData(String firstName, String lastName, String gender, String targetSchool, String currentDate,
                                  String program, String firstSemester, int year, String firstCourse, String firstCourseGrade, String additionalCourses, String additionalCoursesGrades,
                                  String personalCharacteristics, String academicCharacteristics) {
        String sql = "INSERT INTO recommendations (first_name, last_name, gender, target_school, current_date, " +
                "program, first_semester, year, first_course, first_course_grade, additional_courses, additional_courses_grades,"
                + " personal_characteristics, academic_characteristics) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, targetSchool);
            pstmt.setString(5, currentDate);
            pstmt.setString(6, program);
            pstmt.setString(7, firstSemester);
            pstmt.setInt(8, year);
            pstmt.setString(9, firstCourse);
            pstmt.setString(10, firstCourseGrade);
            pstmt.setString(11, additionalCourses);
            pstmt.setString(12, additionalCoursesGrades);
            pstmt.setString(13, personalCharacteristics);
            pstmt.setString(14, academicCharacteristics);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
    
    public static ObservableList<String> loadList(String listName) {
        String sql = "SELECT * FROM " + listName;
        ObservableList<String> list = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading list: " + e.getMessage());
        }
        return list;
    }

    public static void saveList(String listName, ObservableList<String> list) {
        // First, clear the table
        String clearSql = "DELETE FROM " + listName;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(clearSql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error clearing list: " + e.getMessage());
        }

        // Now, insert the new data
        String insertSql = "INSERT INTO " + listName + " (name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            for (String item : list) {
                pstmt.setString(1, item);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error saving list: " + e.getMessage());
        }
    }
    
    public static void createListTable(String listName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + listName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    
    public static void updateData(int id, String firstName, String lastName, String gender, String targetSchool, String currentDate,
            String program, String firstSemester, int year, String firstCourse, String firstCourseGrade, String additionalCourses, String additionalCoursesGrades,
            String personalCharacteristics, String academicCharacteristics) {
		String sql = "UPDATE recommendations SET first_name = ?, last_name = ?, gender = ?, target_school = ?, current_date = ?, " +
		"program = ?, first_semester = ?, year = ?, first_course = ?, first_course_grade = ?, additional_courses = ?, additional_courses_grades = ?, " +
		"personal_characteristics = ?, academic_characteristics = ? WHERE id = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_URL);
			  PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, gender);
			pstmt.setString(4, targetSchool);
			pstmt.setString(5, currentDate);
			pstmt.setString(6, program);
			pstmt.setString(7, firstSemester);
			pstmt.setInt(8, year);
			pstmt.setString(9, firstCourse);
			pstmt.setString(10, firstCourseGrade);
			pstmt.setString(11, additionalCourses);
			pstmt.setString(12, additionalCoursesGrades);
			pstmt.setString(13, personalCharacteristics);
			pstmt.setString(14, academicCharacteristics);
			pstmt.setInt(15, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error updating data: " + e.getMessage());
		}
	}
    
    public static void deleteData(int id) {
        String sql = "DELETE FROM recommendations WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting data: " + e.getMessage());
        }
    }

    // Main method to test the database connection and table creation
    public static void main(String[] args) {
        // Try to establish a connection to the database
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // If the connection is successful, print a confirmation message
            System.out.println("Connected to database");
            createTable();
        } catch (SQLException e) {
            // If there is an error connecting to the database, print an error message
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
}
