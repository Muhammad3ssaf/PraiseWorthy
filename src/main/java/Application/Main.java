package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Initialize and show the primary stage with the login scene
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
        
        // Set the callback for when the login is successful
        LoginController.setOnLoginSuccessful(() -> {
            primaryStage.close(); // Close the login stage
            showRecommendationForm(); // Open the recommendation form
            switchScene("ChangePassword.fxml", "Change Password", primaryStage);
        });
    }

    private void showRecommendationForm() {
        RecommendationForm recommendationForm = new RecommendationForm();
        Stage primaryStage = new Stage();
        try {
            DataBaseManager.createTable(); // Initialize the database and create the table
            recommendationForm.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
    
    // Method to switch between scenes
    public static void switchScene(String fxml, String title, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
