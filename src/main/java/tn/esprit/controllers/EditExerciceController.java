package tn.esprit.controllers;
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
import tn.esprit.services.exerciceService;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EditExerciceController {

    @FXML
    private TextField nomField;
    @FXML
    private TextArea descriptionField;

    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> muscleComboBox;



    private exercice currentExercice;
    private final exerciceService es = new exerciceService();

    // Méthode pour initialiser le formulaire avec les données de l'exercice
    public void setExerciceData(exercice ex) {
        currentExercice = ex;
        nomField.setText(ex.getNOM());
        descriptionField.setText(ex.getDESCRIPTION());
        imageView.setImage(new Image(ex.getIMAGE_URL()));
        muscleComboBox.setValue(ex.getMUSCLE_CIBLE());
        populateMuscleTargetComboBox();

    }
    // Méthode pour sauvegarder les modifications
    @FXML
    private void saveExercice() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit this exercise?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            currentExercice.setNOM(nomField.getText());
            currentExercice.setDESCRIPTION(descriptionField.getText());
            currentExercice.setIMAGE_URL(imageView.getImage().getUrl());
            currentExercice.setMUSCLE_CIBLE(muscleComboBox.getValue());

            try {
                es.modifier(currentExercice);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The exercise has been successfully edited!");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the error
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imageUrl = file.toURI().toString();
            System.out.println("Image URL: " + imageUrl); // Print the image URL for debugging
            currentExercice.setIMAGE_URL(imageUrl);
            imageView.setImage(new Image(imageUrl));
            try {
                es.modifier(currentExercice); // update the plan in the database
            } catch (SQLException e) {
                System.err.println("Failed to update the plan: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected"); // Print a message if no file was selected
        }


    }
    private void populateMuscleTargetComboBox() {
        try {
            List<String> muscleTargets = es.getUniqueMuscleTargets();
            muscleComboBox.getItems().addAll(muscleTargets);
        } catch (SQLException e) {
            System.err.println("Failed to populate muscle target ComboBox: " + e.getMessage());
        }
    }

    public exercice getExecice() {
        return currentExercice;
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


