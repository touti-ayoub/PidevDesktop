package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Competition;

import java.io.IOException;


public class afficherCompetition {

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label libelleLabel;

    @FXML
    private Label nbrMaxLabel;

    @FXML
    private Button modifierC;

    private Competition selectedCompetition; // Déclarer selectedCompetition ici

    public void initData(Competition competition) {
        selectedCompetition = competition;
        libelleLabel.setText("Compétition: " + competition.getLibelle());
        dateDebutLabel.setText("Date de début: " + competition.getDateDebut());
        dateFinLabel.setText("Date de fin: " + competition.getDateFin().toString());
        nbrMaxLabel.setText("Nombre max membres: " + competition.getNbrMaxMembres());
    }
    @FXML
    private void handleModifierButtonClick(ActionEvent event) {
        // Utilisez la compétition actuellement affichée
        if (selectedCompetition != null) {
            loadModifierCompetitionInterface(selectedCompetition);
        }
    }

    private void loadModifierCompetitionInterface(Competition competition) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierCompetition.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la nouvelle interface et transmettez les données
            modifierCompetition controller = loader.getController();
            controller.initData(competition);

            // Créez une nouvelle scène et configurez-la dans votre stage principal
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Montrez la nouvelle scène
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
        }
    }




}
