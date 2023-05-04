package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class LoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;

    private static final String FIRST_TIME_LOGIN_KEY = "firstTimeLogin";
    private static final String PASSWORD_KEY = "defaultPassword";
    private static final String PROPERTIES_FILE = "app.properties";
    private Properties properties;
    // Constructor: Initializes properties and loads the properties file
    public LoginController() {
        properties = new Properties();
        try {
            InputStream input = new FileInputStream(PROPERTIES_FILE);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setOnChangePasswordCallback(Consumer<ActionEvent> callback) {
    }
    public static void setOnRecommendationFormCallback(Consumer<ActionEvent> callback) {
    }
    public static void setOnLoginSuccessful(Runnable callback) {
    }

    // Checks if it's the first-time login
    private boolean isFirstTimeLogin() {
        return Boolean.parseBoolean(properties.getProperty(FIRST_TIME_LOGIN_KEY, "true"));
    }

    // Gets the stored password
    private String getPassword() {
        return properties.getProperty(PASSWORD_KEY, "defaultPassword");
    }

    // Handles the login button click event
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String enteredPassword = passwordField.getText();
        String storedPassword = getPassword();
        boolean isFirstTimeLogin = isFirstTimeLogin();

        if (enteredPassword.equals(storedPassword)) {
            if (isFirstTimeLogin) {
                redirectToChangePassword(event);
            } else {
                redirectToHomePage(event);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Password");
            alert.setContentText("Entered password is incorrect. Please try again.");
            alert.showAndWait();
        }
    }




    // Redirects the user to the change password page
    @FXML
    private void redirectToChangePassword(ActionEvent event) {
    	Main.switchScene("ChangePassword.fxml", "Reset Password", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    // Redirects the user to the home page
    private void redirectToHomePage(ActionEvent event) {
        Main.switchScene("Home.fxml", "Home", (Stage) ((Node) event.getSource()).getScene().getWindow());

    }
}

