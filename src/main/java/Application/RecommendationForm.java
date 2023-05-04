package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.CheckComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class RecommendationForm {

    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private ComboBox<String> genderComboBox;
    private TextField targetSchoolTextField;

    private DatePicker currentDatePicker;
    private ComboBox<String> programComboBox;
    private ComboBox<String> semesterComboBox;
    private ComboBox<String> firstCourseComboBox;
    private TextField yearTextField;
    private TextField firstCourseGradeTextField;
    private CheckComboBox<String> personalCharListView;
    private CheckComboBox<String> academicCharListView;
    private CheckComboBox<String> coursesCheckComboBox;
    private Map<String, TextField> courseGradeTextFields;
    private int formId = -1; // -1 means no ID assigned


    public Scene createRecommendationFormScene() {

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
        ObservableList<String> programs = FXCollections.observableArrayList("Master of Science (MS)", "Master of Business Administration (MBA)",
                "Doctor of Philosophy (PhD)");
        programComboBox = createEditableComboBox(programs);
        gridPane.add(programComboBox, 1, 6);
        gridPane.add(createOptionsEditor(programs), 3, 6); // Add options editor for programs
        // Semester
        Label semesterLabel = new Label("Semester:");
        gridPane.add(semesterLabel, 0, 7);
        ObservableList<String> semesters = FXCollections.observableArrayList("Spring", "Fall", "Summer");
        semesterComboBox = createEditableComboBox(semesters);
        gridPane.add(semesterComboBox, 1, 7);
        // Year 
        Label yearLabel = new Label("Year:");
        yearTextField = new TextField();
        HBox yearBox = new HBox(yearLabel, yearTextField);
        yearBox.setAlignment(Pos.CENTER); // Center-align yearLabel and yearTextField
        yearBox.setSpacing(10); // Add some spacing between yearLabel and yearTextField
        gridPane.add(yearBox, 2, 7); // Update column position to 2

        gridPane.add(createOptionsEditor(semesters), 3, 7); // Update column position to 3
        // First Course
        Label firstCourseLabel = new Label("First Course Taken with Professor:");
        gridPane.add(firstCourseLabel, 0, 8);
        ObservableList<String> firstCourses = FXCollections.observableArrayList("CS151: Object-Oriented Design", "CS166: Information Security",
                "CS154: Theory of Computation", "CS160: Software Engineering", "CS256: Cryptography",
                "CS146: Data Structures and Algorithms", "CS152: Programming Languages Paradigm");
        firstCourseComboBox = createEditableComboBox(firstCourses);
        gridPane.add(firstCourseComboBox, 1, 8);
        gridPane.add(createOptionsEditor(firstCourses), 3, 8); // Update column position to 3
        // First Course grade 
        Label firstCourseGradeLabel = new Label("Grade:");
        firstCourseGradeTextField = new TextField();
        HBox firstCourseGradeBox = new HBox(firstCourseGradeLabel, firstCourseGradeTextField);
        firstCourseGradeBox.setAlignment(Pos.CENTER); // Center-align yearLabel and yearTextField
        firstCourseGradeBox.setSpacing(10); // Add some spacing between yearLabel and yearTextField
        gridPane.add(firstCourseGradeBox, 2, 8); // Update column position to 2
        // Additional Courses taken with professor
        Label coursesLabel = new Label("Additional Courses Taken with Professor:");
        gridPane.add(coursesLabel, 0, 9);
        ObservableList<String> courses = FXCollections.observableArrayList("CS151: Object-Oriented Design", "CS166: Information Security",
                "CS154: Theory of Computation", "CS160: Software Engineering", "CS256: Cryptography",
                "CS146: Data Structures and Algorithms", "CS152: Programming Languages Paradigm");
        VBox coursesWithGrades = createCoursesComboBoxWithGradeFields(courses);
        gridPane.add(coursesWithGrades, 1, 9);
        gridPane.add(createOptionsEditor(courses), 3, 9); // Add options editor for courses

        // Personal characteristics
        Label personalCharLabel = new Label("Personal Characteristics:");
        gridPane.add(personalCharLabel, 0, 10);
        ObservableList<String> personalCharItems = FXCollections.observableArrayList("very passionate", "very enthusiastic", "punctual",
                "attentive", "polite");
        personalCharListView = createCheckComboBox(personalCharItems); // Assign to personalCharListView
        gridPane.add(personalCharListView, 1, 10); // Use personalCharListView here
        // Add options editor for personal characteristics
        gridPane.add(createOptionsEditor(personalCharItems), 3, 10);

        // Academic characteristics
        Label academicCharLabel = new Label("Academic Characteristics:");
        gridPane.add(academicCharLabel, 0, 11);
        ObservableList<String> academicCharItems = FXCollections.observableArrayList("submitted well-written assignments", "participated in all of my class activities",
                "worked hard", "was very well prepared for every exam and assignment", "picked up new skills quickly",
                "was able to excel academically at the top of my class");
        academicCharListView = createCheckComboBox(academicCharItems); // Assign to academicCharListView
        gridPane.add(academicCharListView, 1, 11); // Use academicCharListView here
        // Add options editor for academic characteristics
        gridPane.add(createOptionsEditor(academicCharItems), 3, 11);

        // Compile button
        Button compileButton = new Button("Compile");
        gridPane.add(compileButton, 1, 12);
        GridPane.setHalignment(compileButton, javafx.geometry.HPos.RIGHT);
        compileButton.setOnAction(event -> handleCompileButton());
        
        // Delete button
        Button deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 2, 12);
        GridPane.setHalignment(deleteButton, javafx.geometry.HPos.RIGHT);
        deleteButton.setOnAction(event -> handleDeleteButton());
        
        // Back button 
        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        GridPane.setHalignment(backButton, javafx.geometry.HPos.LEFT);
        backButton.setOnAction(event -> redirectToHomePage(event));
        
        Scene scene = new Scene(gridPane, 1200, 900);
        return scene;
    }
    
    private void handleDeleteButton() {
        if (formId != -1) {
            DataBaseManager.deleteData(formId);
            formId = -1; // Reset the formId
            clearForm(); // Clear form data
        }
    }


    private void handleCompileButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecommendationPreview.fxml"));
            Parent previewParent = loader.load();
            RecommendationPreviewController controller = loader.getController();

            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String gender = genderComboBox.getSelectionModel().getSelectedItem();
            String targetSchool = targetSchoolTextField.getText();
            String currentDate = currentDatePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
            String program = programComboBox.getSelectionModel().getSelectedItem();
            String firstSemester = semesterComboBox.getSelectionModel().getSelectedItem();
            int year = Integer.parseInt(yearTextField.getText());
            String firstCourse = firstCourseComboBox.getSelectionModel().getSelectedItem();
            String firstCourseGrade = firstCourseGradeTextField.getText();
            
            // for preview only
            String personalCharacteristics = joinWithCommasAnd(personalCharListView.getCheckModel().getCheckedItems());
            String academicCharacteristics = joinWithCommasAnd(academicCharListView.getCheckModel().getCheckedItems());
            String additionalCoursesAndGrades = formatCoursesAndGradesString(coursesCheckComboBox, courseGradeTextFields);
            // for database only
            String personalCharsList = String.join(", ", personalCharListView.getCheckModel().getCheckedItems());
            String academicCharsList = String.join(", ", academicCharListView.getCheckModel().getCheckedItems());
            String additionalCourses = String.join(", ", coursesCheckComboBox.getCheckModel().getCheckedItems());
    	    String additionalCoursesGrades = courseGradeTextFields.entrySet().stream()
    	            .filter(entry -> coursesCheckComboBox.getCheckModel().isChecked(entry.getKey()))
    	            .map(entry -> entry.getValue().getText())
    	            .collect(Collectors.joining(", "));
    	   
    	    if (formId == -1) {
    	        DataBaseManager.insertData(firstName, lastName, gender, targetSchool, currentDate, program, firstSemester, year, firstCourse, firstCourseGrade, additionalCourses, additionalCoursesGrades, personalCharsList, academicCharsList);
    	    } else {
    	        DataBaseManager.updateData(formId, firstName, lastName, gender, targetSchool, currentDate, program, firstSemester, year, firstCourse, firstCourseGrade, additionalCourses, additionalCoursesGrades, personalCharsList, academicCharsList);
    	    }            controller.setData(firstName, lastName, gender, targetSchool, currentDate, program, firstSemester, year,
                    firstCourse, firstCourseGrade, additionalCoursesAndGrades, personalCharacteristics, academicCharacteristics);
    	    showSuccessMessage();
            Stage stage = new Stage();
            stage.setTitle("Recommendation Preview");
            stage.setScene(new Scene(previewParent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearForm() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        genderComboBox.getSelectionModel().selectFirst();
        targetSchoolTextField.clear();
        currentDatePicker.setValue(null);
        programComboBox.getSelectionModel().clearSelection();
        semesterComboBox.getSelectionModel().clearSelection();
        yearTextField.clear();
        firstCourseComboBox.getSelectionModel().clearSelection();
        firstCourseGradeTextField.clear();
        coursesCheckComboBox.getCheckModel().clearChecks();
        personalCharListView.getCheckModel().clearChecks();
        academicCharListView.getCheckModel().clearChecks();
    }


    private String joinWithCommasAnd(List<String> items) {
        StringBuilder result = new StringBuilder();
        int size = items.size();

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                if (i == size - 1) {
                    result.append(" and ");
                } else {
                    result.append(", ");
                }
            }
            result.append(items.get(i));
        }

        return result.toString();
    }

    
    private void redirectToHomePage(ActionEvent event) {
        try {
            Parent homeParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene homeScene = new Scene(homeParent, 400, 300);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.setTitle("Home");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void start(Stage primaryStage) {
        Scene scene = createRecommendationFormScene();
        primaryStage.setTitle("Recommendation Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSuccessMessage() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Form Submission");
        successAlert.setContentText("The recommendation form has been submitted successfully.");
        successAlert.showAndWait();
    }
    
 // Update ComboBox or ListView items
    private void updateItems(ObservableList<String> items, String action) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(action + " Option");
        inputDialog.setHeaderText(action + " an option in the list");
        inputDialog.setContentText("Enter the option name:");

        Optional<String> result = inputDialog.showAndWait();
        result.ifPresent(option -> {
            if (action.equals("Add")) {
                items.add(option);
            } else if (action.equals("Edit")) {
                int selectedIndex = items.indexOf(option);
                if (selectedIndex != -1) {
                    items.set(selectedIndex, option);
                }
            } else if (action.equals("Delete")) {
                items.remove(option);
            }
        });
    }

    private ComboBox<String> createEditableComboBox(ObservableList<String> items) {
        ComboBox<String> comboBox = new ComboBox<>(items);
        comboBox.setEditable(true);

        return comboBox;
    }

    private VBox createCoursesComboBoxWithGradeFields(ObservableList<String> courses) {
        CheckComboBox<String> coursesComboBox = createCheckComboBox(courses);
        coursesCheckComboBox = coursesComboBox;
        VBox coursesWithGrades = new VBox();
        courseGradeTextFields = new HashMap<>();

        coursesComboBox.getCheckModel().getCheckedItems().addListener((javafx.collections.ListChangeListener<String>) c -> {
            coursesWithGrades.getChildren().clear();
            for (String course : coursesComboBox.getCheckModel().getCheckedItems()) {
                TextField gradeTextField = courseGradeTextFields.computeIfAbsent(course, k -> new TextField());
                HBox courseWithGrade = new HBox(new Label(course), gradeTextField);
                courseWithGrade.setSpacing(10);
                coursesWithGrades.getChildren().add(courseWithGrade);
            }
        });

        VBox coursesContainer = new VBox(coursesComboBox, coursesWithGrades);
        coursesContainer.setSpacing(5);

        return coursesContainer;
    }

    private HBox createOptionsEditor(ObservableList<String> items) {
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(event -> updateItems(items, "Add"));
        deleteButton.setOnAction(event -> updateItems(items, "Delete"));

        HBox optionsEditor = new HBox(addButton, deleteButton);
        optionsEditor.setSpacing(5);

        return optionsEditor;
    }
    private CheckComboBox<String> createCheckComboBox(ObservableList<String> items) {
        CheckComboBox<String> checkComboBox = new CheckComboBox<>(items);
        checkComboBox.setMinWidth(250); // Set a minimum width
        checkComboBox.setMaxWidth(250); // Set a maximum width
        return checkComboBox;
    }
    private String formatCoursesAndGradesString(CheckComboBox<String> coursesCheckComboBox, Map<String, TextField> courseGradeTextFields) {
        List<String> courses = coursesCheckComboBox.getCheckModel().getCheckedItems();

        if (courses.isEmpty()) {
            return "";
        }

        Map<String, String> grades = courseGradeTextFields.entrySet().stream()
                .filter(entry -> coursesCheckComboBox.getCheckModel().isChecked(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getText()));

        StringBuilder coursesAndGrades = new StringBuilder();

        for (int i = 0; i < courses.size(); i++) {
            String course = courses.get(i);
            String grade = grades.get(course);

            if (i > 0) {
                if (i == courses.size() - 1) {
                    coursesAndGrades.append(" and");
                } else {
                    coursesAndGrades.append(",");
                }
            }

            coursesAndGrades.append(" \"").append(grade).append("\" from my \"").append(course).append("\"");
        }

        return coursesAndGrades.toString();
    }

    public void showForm(Scene recommendationFormScene, int id, String firstName, String lastName, String gender, String targetSchool, String currentDate, String program, String firstSemester, int year, String firstCourse, String firstCourseGrade, String additionalCourses, String additionalCoursesGrades, String personalCharacteristics, String academicCharacteristics) {
        // Populate form fields
	    firstNameTextField.setText(firstName);
	    lastNameTextField.setText(lastName);
	    genderComboBox.getSelectionModel().select(gender);
	    targetSchoolTextField.setText(targetSchool);
	    currentDatePicker.setValue(LocalDate.parse(currentDate));
	    programComboBox.getSelectionModel().select(program);
	    semesterComboBox.getSelectionModel().select(firstSemester);
	    yearTextField.setText(String.valueOf(year));
	    firstCourseComboBox.getSelectionModel().select(firstCourse);
	    firstCourseGradeTextField.setText(firstCourseGrade);
	    formId = id;
	
	    // Set additional courses and grades
	    if (!additionalCourses.isEmpty() && !additionalCoursesGrades.isEmpty()) {
	        String[] addCourses = additionalCourses.split(", ");
	        String[] addCoursesGrades = additionalCoursesGrades.split(", ");
	        for (int i = 0; i < addCourses.length; i++) {
	            String course = addCourses[i];
	            coursesCheckComboBox.getCheckModel().check(course);
	            courseGradeTextFields.get(course).setText(addCoursesGrades[i]);
	        }
	    }
	
	    // Set personal characteristics
	    String[] personalChars = personalCharacteristics.split(", ");
	    for (String characteristic : personalChars) {
	        personalCharListView.getCheckModel().check(characteristic);
	    }
	
	    // Set academic characteristics
	    String[] academicChars = academicCharacteristics.split(", ");
	    for (String characteristic : academicChars) {
	        academicCharListView.getCheckModel().check(characteristic);
	    }
	
	    // Display the form
	    try {
	    	Stage stage = new Stage();
	    	stage.setScene(recommendationFormScene);
	    	stage.setTitle("Recommendation Form");
	    	stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}




}
