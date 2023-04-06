package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void switchScene(String fxml, String title, Stage stage) {
        try {
            Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(pane, 300, 200);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(pane);
            }
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
