package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ChangePasswordController {

    private static final String FIRST_TIME_LOGIN_KEY = "firstTimeLogin";
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private TextField usernameField;

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "defaultPassword";
    private static final String PROPERTIES_FILE = "app.properties";
    private Properties properties;

    public ChangePasswordController() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handles the change password button click event
    @FXML
    private void handleChangePassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmedPassword = confirmNewPasswordField.getText();

        // Check if the passwords match
        if (!newPassword.equals(confirmedPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords do not match");
            alert.setContentText("The entered passwords do not match. Please try again.");
            alert.showAndWait();
            return;
        }

        // Save the new password in the properties file
        Properties properties = new Properties();
        try {
            OutputStream output = new FileOutputStream(PROPERTIES_FILE);
            properties.setProperty(FIRST_TIME_LOGIN_KEY, "false");
            properties.setProperty(PASSWORD_KEY, newPassword);
            properties.store(output, null);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the change password window
        Stage window = (Stage) newPasswordField.getScene().getWindow();
        window.close();
    }
    @FXML
    private void handleSavePassword(ActionEvent event) {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();
        String username = usernameField.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty() || username.isEmpty()) {
            // Show an error message if any of the fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        if (!username.equals(properties.getProperty(USERNAME_KEY))) {
            // Show an error message if the entered username is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Username");
            alert.setContentText("The entered username is incorrect. Please try again.");
            alert.showAndWait();
            return;
        }

        if (!validateCurrentPassword(currentPassword)) {
            // Show an error message if the entered current password is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Password");
            alert.setContentText("The entered password is incorrect. Please try again.");
            alert.showAndWait();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            // Show an error message if the new passwords don't match
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("New Passwords Do Not Match");
            alert.setContentText("The new passwords do not match. Please try again.");
            alert.showAndWait();
            return;
        }

        // Save the new password
        properties.setProperty(PASSWORD_KEY, newPassword);

        try {
            properties.store(new FileOutputStream(PROPERTIES_FILE), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show a success message and redirect to the home page
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Password Changed");
        alert.setContentText("Your password has been changed successfully.");
        alert.showAndWait();
        redirectToHomePage(event);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        redirectToHomePage(event);
    }

    private void redirectToHomePage(ActionEvent event) {
        Main.switchScene("Home.fxml", "Home Page", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    private boolean validateCurrentPassword(String enteredPassword) {
        return enteredPassword.equals(properties.getProperty(PASSWORD_KEY));
    }


}
