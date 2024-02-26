package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private ListView<exercice> exercicesListView; // Pour sélectionner les exercices

    private File selectedFile;

    private final planService ps = new planService();
    private final exerciceService es = new exerciceService(); // Pour récupérer la liste des exercices

    @FXML
    private void initialize() throws SQLException {
        // Charger la liste des exercices disponibles
        List<exercice> exercices = es.recuperer();
        ObservableList<exercice> observableExercices = FXCollections.observableArrayList(exercices);
        exercicesListView.setItems((observableExercices)); // Assurez-vous que cette méthode retourne ObservableList
        exercicesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleUploadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour le plan");
        Stage stage = (Stage) nomPlanTextField.getScene().getWindow(); // Assurez-vous que la scène est déjà définie
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

        plan newPlan = new plan(); // Adaptez le constructeur de votre classe plan si nécessaire
        newPlan.setNOM(nom);
        newPlan.setDESCRIPTION(description);
        newPlan.setIMAGE_URL(imageURL); // Assurez-vous que votre classe plan a un champ pour l'URL de l'image

        ps.ajouterPlan(newPlan, selectedExercices); // Votre méthode ajouterPlan doit gérer l'image et les exercices

        // Afficher un message de succès ou effectuer d'autres actions nécessaires après l'ajout
    }
}
