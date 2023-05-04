package Application;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class ProfessorInfoController {
	private static final String PROPERTIES_FILE = "professorInfo.properties";
    @FXML
    private TextField professorNameField, professorTitleField, schoolNameField, departmentNameField, professorEmailField, professorPhoneNumberField;
    @FXML
    private Button saveButton;
    
    @FXML
    public void initialize() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        professorNameField.setText(properties.getProperty("professorName", "Ahmad Yazdankhah"));
        professorTitleField.setText(properties.getProperty("professorTitle", "Lecturer"));
        schoolNameField.setText(properties.getProperty("schoolName", "SJSU"));
        departmentNameField.setText(properties.getProperty("departmentName", "CS department"));
        professorEmailField.setText(properties.getProperty("professorEmail", "ahmad.yazdankhah@sjsu.edu"));
        professorPhoneNumberField.setText(properties.getProperty("professorPhoneNumber", "(123) 456-7890"));
    }

    @FXML
    private void handleSaveButtonClick(ActionEvent event) throws IOException {
    	Properties properties = new Properties();
        properties.setProperty("professorName", professorNameField.getText());
        properties.setProperty("professorTitle", professorTitleField.getText());
        properties.setProperty("schoolName", schoolNameField.getText());
        properties.setProperty("departmentName", departmentNameField.getText());
        properties.setProperty("professorEmail", professorEmailField.getText());
        properties.setProperty("professorPhoneNumber", professorPhoneNumberField.getText());

        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, null);
        }

        redirectToHomePage(event);
    }
    
    
    private void redirectToHomePage(ActionEvent event) {
        Main.switchScene("Home.fxml", "Home", (Stage) ((Node) event.getSource()).getScene().getWindow());

    }
    
}
