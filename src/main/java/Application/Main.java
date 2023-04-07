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
    }

    // Switch to another scene
    public static void switchScene(String fxml, String title, Stage stage) {
        try {
            // Load the new scene
            Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = stage.getScene();

            // Check if the stage has a scene already
            if (scene == null) {
                scene = new Scene(pane, 300, 200);
                stage.setScene(scene);
            } else {
                // If it has, replace the root with the new scene
                stage.getScene().setRoot(pane);
            }

            // Set the stage title and show the stage
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
