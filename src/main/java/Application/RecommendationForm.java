package Application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RecommendationForm extends Application {

    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private ComboBox<String> genderComboBox;
    private TextField targetSchoolTextField;
    private DatePicker currentDatePicker;
    private ComboBox<String> programComboBox;
    private ComboBox<String> semesterComboBox;
    private TextField yearTextField;
    private ListView<String> coursesListView;
    private ListView<String> personalCharListView;
    private ListView<String> academicCharListView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Recommendation Form");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // First name
        Label firstNameLabel = new Label("First Name:");
        gridPane.add(firstNameLabel, 0, 1);
        firstNameTextField = new TextField();
        gridPane.add(firstNameTextField, 1, 1);

        // Last name
        Label lastNameLabel = new Label("Last Name:");
        gridPane.add(lastNameLabel, 0, 2);
        lastNameTextField = new TextField();
        gridPane.add(lastNameTextField, 1, 2);

        // Gender
        Label genderLabel = new Label("Gender:");
        gridPane.add(genderLabel, 0, 3);
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        genderComboBox.getSelectionModel().selectFirst();
        gridPane.add(genderComboBox, 1, 3);

        // Target school
        Label targetSchoolLabel = new Label("Target School:");
        gridPane.add(targetSchoolLabel, 0, 4);
        targetSchoolTextField = new TextField();
        gridPane.add(targetSchoolTextField, 1, 4);

        // Current date
        Label currentDateLabel = new Label("Current Date:");
        gridPane.add(currentDateLabel, 0, 5);
        currentDatePicker = new DatePicker();
        gridPane.add(currentDatePicker, 1, 5);

        // Program
        Label programLabel = new Label("Program:");
        gridPane.add(programLabel, 0, 6);
        programComboBox = new ComboBox<>();
        programComboBox.getItems().addAll("Master of Science (MS)", "Master of Business Administration (MBA)",
                "Doctor of Philosophy (PhD)");
        programComboBox.getSelectionModel().selectFirst();
        gridPane.add(programComboBox, 1, 6);

        // First semester and year
        Label firstSemesterLabel = new Label("First Semester with Professor:");
        gridPane.add(firstSemesterLabel, 0, 7);
        semesterComboBox = new ComboBox<>();
        semesterComboBox.getItems().addAll("Spring", "Fall", "Summer");
        semesterComboBox.getSelectionModel().selectFirst();
        gridPane.add(semesterComboBox, 1, 7);
        Label yearLabel = new Label("Year:");
        gridPane.add(yearLabel, 2, 7);
        yearTextField = new TextField();
        gridPane.add(yearTextField, 3, 7);

        // Courses taken with professor
        Label coursesLabel = new Label("Courses Taken with Professor:");
        gridPane.add(coursesLabel, 0, 8);
        coursesListView = new ListView<>();
        coursesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        coursesListView.getItems().addAll("CS151: Object-Oriented Design", "CS166: Information Security",
                "CS154: Theory of Computation", "CS160: Software Engineering", "CS256: Cryptography",
                "CS146: Data Structures and Algorithms", "CS152: Programming Languages Paradigm");
        gridPane.add(coursesListView, 1, 8);
        // Letter grades for courses
        Label gradesLabel = new Label("Letter Grades:");
        gridPane.add(gradesLabel, 2, 8);
        TextField letterGradesTextField = new TextField();
        gridPane.add(letterGradesTextField, 3, 8);

        // Personal characteristics
        Label personalCharLabel = new Label("Personal Characteristics:");
        gridPane.add(personalCharLabel, 0, 9);
        personalCharListView = new ListView<>();
        personalCharListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personalCharListView.getItems().addAll("Very passionate", "Very enthusiastic", "Punctual",
                "Attentive", "Polite");
        gridPane.add(personalCharListView, 1, 9);

        // Academic characteristics
        Label academicCharLabel = new Label("Academic Characteristics:");
        gridPane.add(academicCharLabel, 0, 10);
        academicCharListView = new ListView<>();
        academicCharListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        academicCharListView.getItems().addAll("Submitted well-written assignments", "Participated in all of my class activities",
                "Worked hard", "Was very well prepared for every exam and assignment", "Picked up new skills quickly",
                "Was able to excel academically at the top of my class");
        gridPane.add(academicCharListView, 1, 10);

        // Submit button
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 11);
        GridPane.setHalignment(submitButton, javafx.geometry.HPos.RIGHT);

        Scene scene = new Scene(gridPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
