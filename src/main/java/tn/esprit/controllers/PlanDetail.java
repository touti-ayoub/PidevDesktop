package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.planService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlanDetail {

    public VBox exercicesContainer;
    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    private planService ps = new planService();
    private plan currentPlan;


    public void setPlan(plan p) throws SQLException {
        this.currentPlan = p;
        nameLabel.setText(p.getNOM());
        descriptionLabel.setText(p.getDESCRIPTION());
        imageView.setImage(new Image(p.getIMAGE_URL()));
        loadExercisesForPlan(p.getID());
    }

    private void loadExercisesForPlan(int planId) throws SQLException {
        exercicesContainer.getChildren().clear(); // Efface les exercices précédents
        List<exercice> exercices = ps.getExercicesForPlan(planId); // Implémentez cette méthode dans planService
        for (exercice ex : exercices) {
            HBox hbox = new HBox(3); // Espacement de 10 entre les éléments
            Label label = new Label(ex.getNOM());
            ImageView imageView = new ImageView();

                if (ex.getIMAGE_URL() != null) {
                    imageView.setImage(new Image(ex.getIMAGE_URL()));
                }
            imageView.setFitHeight(50); // Hauteur de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            hbox.getChildren().addAll(imageView, label);
            exercicesContainer.getChildren().add(hbox);
        }
    }

    @FXML
    private void handleDeleteButton() throws SQLException {
        if (currentPlan != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this plan?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                ps.supprimer(currentPlan.getID());
                // Refresh the view or close the window
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    @FXML
    private void handleModifyButton() {
        if (currentPlan != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditPlan.fxml"));

                // Create the scene
                Scene scene = new Scene(fxmlLoader.load());

                // Get the controller
                EditPlanController controller = fxmlLoader.getController();
                controller.setPlanData(currentPlan); // Assuming you have a setPlan method in EditPlanController

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(scene);

                // Show the stage
                stage.showAndWait();
                // After closing the stage, update the current plan
                currentPlan = controller.getPlan(); // Assuming you have a getPlan method in EditPlanController

                // Update the plan in the database
                ps.modifier(currentPlan);

                // Refresh the view
            } catch (IOException e) {
                System.err.println("Failed to load the page: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Failed to update the plan: " + e.getMessage());
            }
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
}