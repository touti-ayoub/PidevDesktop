package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditExerciceController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField muscleField;
    @FXML
    private ImageView imageView;



    private exercice currentExercice;
    private final exerciceService es = new exerciceService();

    // Méthode pour initialiser le formulaire avec les données de l'exercice
    public void setExerciceData(exercice ex) {
        currentExercice = ex;
        nomField.setText(ex.getNOM());
        descriptionField.setText(ex.getDESCRIPTION());
        muscleField.setText(ex.getMUSCLE_CIBLE());
        imageView.setImage(new Image(ex.getIMAGE_URL()));

    }

    // Méthode pour sauvegarder les modifications
    @FXML
    private void saveExercice() {
        currentExercice.setNOM(nomField.getText());
        currentExercice.setDESCRIPTION(descriptionField.getText());
        currentExercice.setMUSCLE_CIBLE(muscleField.getText());
        currentExercice.setIMAGE_URL(imageView.getImage().getUrl());

        try {
            es.modifier(currentExercice);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("L'exercice a été modifié avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez l'erreur
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

    public exercice getExecice() {
        return currentExercice;
    }
}
