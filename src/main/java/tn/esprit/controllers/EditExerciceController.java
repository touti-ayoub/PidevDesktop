package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;
import java.sql.PreparedStatement;

public class EditExerciceController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField muscleField;



    private exercice currentExercice;
    private final exerciceService es = new exerciceService();

    // Méthode pour initialiser le formulaire avec les données de l'exercice
    public void setExerciceData(exercice ex) {
        currentExercice = ex;
        nomField.setText(ex.getNOM());
        descriptionField.setText(ex.getDESCRIPTION());
        muscleField.setText(ex.getMUSCLE_CIBLE());

    }

    // Méthode pour sauvegarder les modifications
    @FXML
    private void saveExercice() {
        currentExercice.setNOM(nomField.getText());
        currentExercice.setDESCRIPTION(descriptionField.getText());
        currentExercice.setMUSCLE_CIBLE(muscleField.getText());

        try {
            es.modifier(currentExercice);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Le plan a été ajouté avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez l'erreur
        }
    }
}
