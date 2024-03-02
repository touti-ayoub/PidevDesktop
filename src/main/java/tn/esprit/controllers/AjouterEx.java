package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterEx {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> MuscleComboBox;


    @FXML
    private Stage stage;
    @FXML
    private File selectedFile;




    @FXML
    private void initialize() {
        populateMuscleTargetComboBox();
    }
    @FXML
    private void handleUploadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Charger l'image sélectionnée dans l'ImageView
            imageView.setImage(new javafx.scene.image.Image(new FileInputStream(selectedFile)));
        }
    }

    private final exerciceService es = new exerciceService();
    @FXML
    private void handleAddExercise() {
        String name = nameTextField.getText();
        String description = descriptionTextArea.getText();
        String muscle_cible =  nameTextField.getText();
        String imageURL = "";
        if (selectedFile != null) {
            // Chemin de l'image pour la base de données
            imageURL = selectedFile.toURI().toString();
        }
        exercice exercise = new exercice(name, description,muscle_cible, imageURL);

        try {
            es.ajouter(exercise);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Le plan a été ajouté avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();

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


    private void populateMuscleTargetComboBox() {
        try {
            List<String> muscleTargets = es.getUniqueMuscleTargets();
            MuscleComboBox.getItems().addAll(muscleTargets);
        } catch (SQLException e) {
            System.err.println("Failed to populate muscle target ComboBox: " + e.getMessage());
        }
    }


}
