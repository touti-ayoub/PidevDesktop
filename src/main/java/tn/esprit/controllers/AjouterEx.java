package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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

public class AjouterEx {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ImageView imageView;

    @FXML
    private Stage stage;
    @FXML
    private File selectedFile;




    @FXML
    private void initialize() {
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
    void navigate(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherEx.fxml"));
            nameTextField.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }



}
