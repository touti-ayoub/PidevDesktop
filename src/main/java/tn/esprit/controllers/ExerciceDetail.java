package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;


public class ExerciceDetail {


    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label muscleLabel;
    @FXML
    private ImageView imageView;



    private exerciceService es = new exerciceService();
    private exercice currentExercice;

    public void setExercice(exercice e) throws SQLException {
        this.currentExercice = e;
        nameLabel.setText(e.getNOM());
        descriptionLabel.setText(e.getDESCRIPTION());
        imageView.setImage(new Image(e.getIMAGE_URL()));
        muscleLabel.setText(e.getMUSCLE_CIBLE());

    }
    @FXML
    private void handleDeleteButton() throws SQLException {
        if (currentExercice != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this exercise?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                es.supprimer(currentExercice.getID());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The exercise has been successfully deleted!");
                alert.showAndWait();
                // Refresh the view or close the window
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }
    public void AjouterExercice(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));

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

