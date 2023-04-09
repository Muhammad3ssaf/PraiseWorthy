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
    private static Runnable onLoginSuccessful;
    private static Consumer<ActionEvent> onChangePasswordCallback;
    private static Consumer<ActionEvent> onRecommendationFormCallback;
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
        onChangePasswordCallback = callback;
    }
    public static void setOnRecommendationFormCallback(Consumer<ActionEvent> callback) {
        onRecommendationFormCallback = callback;
    }
    public static void setOnLoginSuccessful(Runnable callback) {
        onLoginSuccessful = callback;
    }

    private void handleSuccessfulLogin(ActionEvent event) {
        if (onLoginSuccessful != null) {
            onLoginSuccessful.run();
        }

        try {
            if (isFirstTimeLogin()) {
                Parent changePasswordParent = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
                Scene changePasswordScene = new Scene(changePasswordParent, 300, 200);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(changePasswordScene);
                window.setTitle("Change Password");
                window.show();
            } else {
                RecommendationForm recommendationForm = new RecommendationForm();
                Scene recommendationFormScene = recommendationForm.createRecommendationFormScene();

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(recommendationFormScene);
                window.setTitle("Recommendation Form");
                window.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//    @FXML
//    private void handleLogin(ActionEvent event) {
//        String enteredPassword;
//        String storedPassword;
//        boolean isFirstTimeLogin;
//
//        enteredPassword = passwordField.getText();
//        storedPassword = getPassword();
//        isFirstTimeLogin = isFirstTimeLogin();
//
//        if (enteredPassword.equals(storedPassword)) {
//            if (isFirstTimeLogin) {
//                redirectToChangePassword(event);
//            } else {
//            	handleSuccessfulLogin(event);
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Incorrect Password");
//            alert.setContentText("Entered password is incorrect. Please try again.");
//            alert.showAndWait();
//        }
//
//    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String enteredPassword = passwordField.getText();
        String storedPassword = getPassword();
        boolean isFirstTimeLogin = isFirstTimeLogin();

        if (enteredPassword.equals(storedPassword)) {
            if (isFirstTimeLogin) {
                redirectToChangePassword(event);
            } else {
                setOnLoginSuccessful(false, event);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Password");
            alert.setContentText("Entered password is incorrect. Please try again.");
            alert.showAndWait();
        }
    }


    public static void setOnLoginSuccessful(boolean isFirstTimeLogin, ActionEvent event) {
        onLoginSuccessful = () -> {
            try {
                RecommendationForm recommendationForm = new RecommendationForm();
                Scene recommendationFormScene = recommendationForm.createRecommendationFormScene();

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(recommendationFormScene);
                window.setTitle("Recommendation Form");
                window.show();

                if (isFirstTimeLogin) {
                	Parent changePasswordParent = FXMLLoader.load(LoginController.class.getResource("ChangePassword.fxml"));
                    Scene changePasswordScene = new Scene(changePasswordParent, 300, 200);

                    Stage changePasswordStage = new Stage();
                    changePasswordStage.setScene(changePasswordScene);
                    changePasswordStage.setTitle("Change Password");
                    changePasswordStage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        onLoginSuccessful.run();
    }

    // Validates the entered password against the stored password
    private boolean validatePassword(String enteredPassword) {
        return enteredPassword.equals(getPassword());
    }

    // Redirects the user to the change password page
    private void redirectToChangePassword(ActionEvent event) {
        try {
            Parent changePasswordParent = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
            Scene changePasswordScene = new Scene(changePasswordParent, 300, 200);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(changePasswordScene);
            window.setTitle("Change Password");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redirects the user to the home page
    private void redirectToHomePage(ActionEvent event) {
        try {
            Parent homeParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene homeScene = new Scene(homeParent, 400, 300);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.setTitle("Home Page");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles the reset password button click event
    @FXML
    private void handleResetPassword(ActionEvent event) {
        try {
            Parent resetPasswordParent = FXMLLoader.load(getClass().getResource("ResetPassword.fxml"));
            Scene resetPasswordScene = new Scene(resetPasswordParent, 300, 200);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(resetPasswordScene);
            window.setTitle("Reset Password");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

