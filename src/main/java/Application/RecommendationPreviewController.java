package Application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RecommendationPreviewController {

    @FXML
    private TextArea previewTextArea;
    private String professorFullName, professorTitle, schoolName, departmentName, professorEmail, professorPhoneNumber;
	private static final String PROPERTIES_FILE = "professorInfo.properties";



    public void setText(String text) {
        previewTextArea.setText(text);
    }

    @FXML
    public void handleSave() {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();

        // Set the initial directory
        File initialDirectory = new File("CompiledRecommendations/");
        if (!initialDirectory.exists()) {
            initialDirectory.mkdir();
        }
        fileChooser.setInitialDirectory(initialDirectory);

        // Set the initial file name
        String studentFullName = previewTextArea.getText().split(" ")[4].split("\n")[0] + previewTextArea.getText().split(" ")[3];
        String defaultFileName = studentFullName + "Recommendation.txt";
        fileChooser.setInitialFileName(defaultFileName);

        // Set the extension filter to allow only text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the save file dialog and get the selected file
        File file = fileChooser.showSaveDialog(previewTextArea.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(previewTextArea.getText());
            } catch (IOException e) {
                System.err.println("Error saving the file: " + e.getMessage());
            }
        }
        Stage stage = (Stage) previewTextArea.getScene().getWindow();
        stage.close();
    }

    public void setData(String firstName, String lastName, String gender, String targetSchool, String currentDate,
            String program, String firstSemester, int year, String firstCourse, String firstCourseGrade, String additionalCoursesAndGrades,
            String personalCharacteristics, String academicCharacteristics) {

        // Read the template from the file
        String template;
        try {
            template = readTemplateFromFile();
        } catch (IOException e) {
            System.err.println("Error reading the template file: " + e.getMessage());
            return;
        }
        updateProfessorInfo();
        // Replace the place holders in the template with the actual data
        
        String primaryPronoun = gender.equals("Male")? "he" : "she"; // You need to get the pronoun based on the student's gender
        String secondaryPronoun = gender.equals("Male")?"him":"her";
        String capitalizedPronoun = primaryPronoun.equals("he")? "He" : "She";
		if(additionalCoursesAndGrades!="") {
			additionalCoursesAndGrades = "\n" + capitalizedPronoun + " also earned " + additionalCoursesAndGrades + "\n";	        	
        }
        String studentFullName = firstName + " " + lastName;
        String recommendationLetter = String.format(template,
                studentFullName, currentDate, studentFullName, program,
                firstName, firstSemester, primaryPronoun, firstCourse,
                firstName, firstCourseGrade, primaryPronoun, additionalCoursesAndGrades, firstName, academicCharacteristics,
                capitalizedPronoun, personalCharacteristics, primaryPronoun,
                capitalizedPronoun, firstName, secondaryPronoun, professorFullName, professorTitle,
                schoolName, departmentName, professorEmail, professorPhoneNumber);

        previewTextArea.setText(recommendationLetter);
    }
    
    
    private String readTemplateFromFile() throws IOException {
        Path templatePath = Paths.get("recommendationTemplate.txt");
        StringBuilder templateContent = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(templatePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                templateContent.append(line).append("\n");
            }
        }

        return templateContent.toString();
    }
    public void updateProfessorInfo() {
    	Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.professorFullName = properties.getProperty("professorName");
        this.professorTitle = properties.getProperty("professorTitle");
        this.schoolName = properties.getProperty("schoolName");
        this.departmentName = properties.getProperty("departmentName");
        this.professorEmail = properties.getProperty("professorEmail");
        this.professorPhoneNumber = properties.getProperty("professorPhoneNumber");
    }
    @FXML
    public void handleDiscard() {
        Stage stage = (Stage) previewTextArea.getScene().getWindow();
        stage.close();
    }
}

