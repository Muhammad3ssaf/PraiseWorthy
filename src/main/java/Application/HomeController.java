package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.IOException;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField nameTextField;

    private String username;
    @FXML
    private Button continueButton;

    // Set the username and update the welcome label
    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    // Handle logout button click event
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Load the login scene and set it as the current scene
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
    @FXML
    public void handleContinue(ActionEvent event) throws IOException {
        RecommendationForm recommendationForm = new RecommendationForm();
        Scene recommendationFormScene = recommendationForm.createRecommendationFormScene();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(recommendationFormScene);
        window.show();
    }

    // Handle the update name button click event
    @FXML
    private void handleUpdateName(ActionEvent event) {
        String newName = nameTextField.getText();
        setUsername(newName);
    }

}
