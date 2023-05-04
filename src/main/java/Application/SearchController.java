package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;




public class SearchController {

    @FXML
    private TextField searchField;
    @FXML
    private TextArea resultTextArea;
	
	private RecommendationForm recommendationForm;

    public SearchController(RecommendationForm recommendationForm) {
        this.recommendationForm = recommendationForm;
    }
    @FXML
    public void handleSearch() {
        String lastName = searchField.getText().trim();
        // Retrieve student data based on the last name
        String query = "SELECT * FROM recommendations WHERE last_name = ?";

        try (Connection conn = DriverManager.getConnection(DataBaseManager.DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, lastName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String gender = rs.getString("gender");
                String targetSchool = rs.getString("target_school");
                String currentDate = rs.getString("current_date");
                String program = rs.getString("program");
                String firstSemester = rs.getString("first_semester");
                int year = rs.getInt("year");
                String firstCourse = rs.getString("first_course");
                String firstCourseGrade = rs.getString("first_course_grade");
                String additionalCourses = rs.getString("additional_courses");
                String additionalCoursesGrades = rs.getString("additional_courses_grades");
                String personalCharacteristics = rs.getString("personal_characteristics");
                String academicCharacteristics = rs.getString("academic_characteristics");

                // Close the search window and open the recommendation form
                Stage stage = (Stage) searchField.getScene().getWindow();
                stage.close();

                Scene recommendationFormScene = recommendationForm.createRecommendationFormScene(); 
                recommendationForm.showForm(recommendationFormScene, id, firstName, lastName, gender, targetSchool, currentDate, program, firstSemester, year, firstCourse, firstCourseGrade, additionalCourses, additionalCoursesGrades, personalCharacteristics, academicCharacteristics);

            } else {
                System.out.println("No student found with the given last name.");
                resultTextArea.setText("No student found with the given last name.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
            resultTextArea.setText("Error retrieving data: " + e.getMessage());

        }
    }



    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Main.switchScene("Home.fxml", "Home", (Stage) ((Node) event.getSource()).getScene().getWindow());

    }

}
