package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    // Database URL
    private static final String DB_URL = "jdbc:sqlite:/home/moody3ssaf/HERE.db";

    // Main method to test the database connection
    public static void main(String[] args) {
        // Try to establish a connection to the database
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // If the connection is successful, print a confirmation message
            System.out.println("Connected to database");
        } catch (SQLException e) {
            // If there is an error connecting to the database, print an error message
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
}
