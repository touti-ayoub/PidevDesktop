package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.services.exerciceService;
import javafx.scene.image.Image;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ExerciceDetail {


    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView imageView;

    private exerciceService es = new exerciceService();
    private exercice currentExercice;

    public void setExercice(exercice e) throws SQLException {
        this.currentExercice = e;
        nameLabel.setText(e.getNOM());
        descriptionLabel.setText(e.getDESCRIPTION());
        imageView.setImage(new Image(e.getIMAGE_URL()));
    }
    @FXML
    private void handleDeleteButton() throws SQLException {
        if (currentExercice != null) {
            es.supprimer(currentExercice.getID());
            // Refresh the view or close the window
        }
    }
    public void AjouterPlan(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterPlan.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }
    public void AfficherExercice(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherEx.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }

    }
    @FXML
    void AfficherPlan(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherPlan.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }
    @FXML
    private void handleModifyButton() {
        if (currentExercice != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditExercice.fxml"));

                // Create the scene
                Scene scene = new Scene(fxmlLoader.load());

                // Get the controller
                EditExerciceController controller = fxmlLoader.getController();
                controller.setExerciceData(currentExercice); // Assuming you have a setPlan method in EditPlanController

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(scene);

                // Show the stage
                stage.showAndWait();
                // After closing the stage, update the current plan
                currentExercice = controller.getExecice(); // Assuming you have a getPlan method in EditPlanController

                // Update the plan in the database
                es.modifier(currentExercice);

                // Refresh the view
            } catch (IOException e) {
                System.err.println("Failed to load the page: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Failed to update the plan: " + e.getMessage());
            }
        }
    }
}

