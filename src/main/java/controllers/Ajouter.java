package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;

import java.io.IOException;
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
    @FXML
    void navigate(ActionEvent event) {
        try {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherEx.fxml"));
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
