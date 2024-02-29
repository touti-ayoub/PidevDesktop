package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.exerciceService;
import tn.esprit.services.planService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterPlan {

    @FXML
    private TextField nomPlanTextField;
    @FXML
    private TextArea descriptionPlanTextArea;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<exercice> exercicesListView;

    private File selectedFile;

    private final planService ps = new planService();
    private final exerciceService es = new exerciceService();

    @FXML
    private void initialize() throws SQLException {
        List<exercice> exercices = es.recuperer();
        ObservableList<exercice> observableExercices = FXCollections.observableArrayList(exercices);
        exercicesListView.setItems((observableExercices));
        exercicesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleUploadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour le plan");
        Stage stage = (Stage) nomPlanTextField.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imageView.setImage(new javafx.scene.image.Image(new FileInputStream(selectedFile)));
        }
    }

    @FXML
    private void handleAddPlan() throws SQLException {
        String nom = nomPlanTextField.getText();
        String description = descriptionPlanTextArea.getText();
        List<exercice> selectedExercices = exercicesListView.getSelectionModel().getSelectedItems();

        String imageURL = selectedFile != null ? selectedFile.toURI().toString() : "";

        plan newPlan = new plan();
        newPlan.setNOM(nom);
        newPlan.setDESCRIPTION(description);
        newPlan.setIMAGE_URL(imageURL);

        ps.ajouterPlan(newPlan, selectedExercices);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Le plan a été ajouté avec succès !");

        alert.showAndWait();


    }
    @FXML
    void navigate(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherPlan.fxml"));
            nomPlanTextField.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void AjouterPlan(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterPlan.fxml"));

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
