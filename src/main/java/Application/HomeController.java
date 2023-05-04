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
    public void handleCreate(ActionEvent event) throws IOException {
    	RecommendationForm recommendationForm = new RecommendationForm();
        Scene recommendationFormScene = recommendationForm.createRecommendationFormScene();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(recommendationFormScene);
        window.setTitle("Recommendation Form");
        window.show();
    }

    @FXML
    public void handleSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
        
        RecommendationForm recommendationForm = new RecommendationForm();
        SearchController searchController = new SearchController(recommendationForm);
        loader.setController(searchController);
        
        Parent searchParent = loader.load();
        Scene searchScene = new Scene(searchParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(searchScene);
        window.setTitle("Search");
        window.show();
    }


    // Handle logout button click event
    @FXML
    private void handleLogout(ActionEvent event) {
    	Main.switchScene("Login.fxml", "Login", (Stage) ((Node) event.getSource()).getScene().getWindow());

    }
    @FXML
    public void handleContinue(ActionEvent event) throws IOException {
        RecommendationForm recommendationForm = new RecommendationForm();
        Scene recommendationFormScene = recommendationForm.createRecommendationFormScene();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(recommendationFormScene);
        window.show();
    }

}
