package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {

    // Database URL
    private static final String DB_URL = "jdbc:sqlite:myDB.sqlite";

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
                "courses TEXT NOT NULL," +
                "grades TEXT NOT NULL," +
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
                                  String program, String firstSemester, int year, String courses, String grades,
                                  String personalCharacteristics, String academicCharacteristics) {
        String sql = "INSERT INTO recommendations (first_name, last_name, gender, target_school, current_date, " +
                "program, first_semester, year, courses, grades, personal_characteristics, academic_characteristics) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            pstmt.setString(9, courses);
            pstmt.setString(10, grades);
            pstmt.setString(11, personalCharacteristics);
            pstmt.setString(12, academicCharacteristics);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
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
