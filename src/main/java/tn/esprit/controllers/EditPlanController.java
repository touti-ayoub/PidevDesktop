package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.exerciceService;
import tn.esprit.services.planService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditPlanController {
    @FXML
    private TextField nomPlan;
    @FXML
    private TextArea descriptionPlan;
    private final planService ps = new planService();


    @FXML
    private ImageView imageView;
    @FXML
    private ListView<exercice> exercicesListView;
    private final exerciceService es = new exerciceService();



    private plan currentPlan;

    public void setPlanData(plan p) throws SQLException {
        currentPlan = p;
        nomPlan.setText(p.getNOM());
        descriptionPlan.setText(p.getDESCRIPTION());
        imageView.setImage(new Image(p.getIMAGE_URL()));
        exercicesListView.setItems(FXCollections.observableArrayList(p.getExercices()));
        loadExercisesForPlan(p.getID());

    }

    private void loadExercisesForPlan(int planId) throws SQLException {
        List<exercice> allExercices = es.recuperer();
        ObservableList<exercice> observableExercices = FXCollections.observableArrayList(allExercices);
        exercicesListView.setItems(observableExercices);
        exercicesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<exercice> planExercices = ps.getExercicesForPlan(planId);
        for (exercice ex : planExercices) {
            exercicesListView.getSelectionModel().select(ex);
        }
    }

    // Méthode pour sauvegarder les modifications
    @FXML
    private void savePlan() throws SQLException{
        currentPlan.setNOM(nomPlan.getText());
        currentPlan.setDESCRIPTION(descriptionPlan.getText());
        currentPlan.setExercices(exercicesListView.getItems());
        currentPlan.setIMAGE_URL(imageView.getImage().getUrl());
        try {
            ps.modifier(currentPlan);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Le plan a été ajouté avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imageUrl = file.toURI().toString();
            System.out.println("Image URL: " + imageUrl); // Print the image URL for debugging
            currentPlan.setIMAGE_URL(imageUrl);
            imageView.setImage(new Image(imageUrl));
            try {
                ps.modifier(currentPlan); // update the plan in the database
            } catch (SQLException e) {
                System.err.println("Failed to update the plan: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected"); // Print a message if no file was selected
        }
    }
        // existing code...

        public plan getPlan() {
            return currentPlan;
        }

        // existing code...

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
    @FXML
    void AjouterExercice(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));

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

}
