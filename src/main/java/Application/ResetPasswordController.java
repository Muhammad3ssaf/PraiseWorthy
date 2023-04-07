package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ResetPasswordController {

    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private static final String PASSWORD_KEY = "defaultPassword";
    private static final String PROPERTIES_FILE = "app.properties";
    private Properties properties;

    @FXML
    private PasswordField currentPasswordField;

    public String getCurrentPassword() {
        return properties.getProperty(PASSWORD_KEY);
    }


    public ResetPasswordController() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!currentPassword.equals(properties.getProperty(PASSWORD_KEY))) {
            // Show an error message if the current password is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect password");
            alert.setContentText("Please enter your current password correctly and try again.");
            alert.showAndWait();
        } else if (newPassword.equals(confirmPassword)) {
            properties.setProperty(PASSWORD_KEY, newPassword);
            try {
                properties.store(new FileOutputStream(PROPERTIES_FILE), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            redirectToLogin(event);
        } else {
            // Show an error message if the passwords don't match
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords do not match");
            alert.setContentText("Please make sure the passwords match and try again.");
            alert.showAndWait();
        }
    }



    @FXML
    private void handleBackToLogin(ActionEvent event) {
        redirectToLogin(event);
    }

    private void redirectToLogin(ActionEvent event) {
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(loginParent, 300, 200);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.setTitle("Login");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
