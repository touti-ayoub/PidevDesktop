package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;

import java.sql.SQLException;

public class Ajouter {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField musclecibleTF;

    @FXML
    private TextField nomTF;

    private final exerciceService es = new exerciceService();

    @FXML
    void ajouter(ActionEvent event)  {
        try {
            es.ajouter(new exercice(nomTF.getText(),descriptionTF.getText(),musclecibleTF.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
