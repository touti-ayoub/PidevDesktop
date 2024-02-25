package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Competition;
import tn.esprit.services.CompetitionService;

import java.sql.SQLException;

public class modifierCompetition {

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TextField libelle;

    @FXML
    private Button modifierC;

    @FXML
    private TextField nbr;
    private Competition competition;
    private CompetitionService competitionService; // Ajoutez le service

    public modifierCompetition() {
        // Initialiser votre service ici
        this.competitionService = new CompetitionService();
    }
    public void initData(Competition competition) {
        // Initialisez les champs avec les informations de la compétition
        libelle.setText(competition.getLibelle());
        dateDebut.setValue(competition.getDateDebut().toLocalDate());
        dateFin.setValue(competition.getDateFin().toLocalDate());
        nbr.setText(String.valueOf(competition.getNbrMaxMembres()));
    }

    @FXML
    private void handleModifierButtonClick(ActionEvent event) {
        // Vérifiez si la compétition a été initialisée
        if (competition != null) {
            try {
                // Mise à jour de la compétition en utilisant le service
                competitionService.modifier(competition);

                // Affichez une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setHeaderText(null);
                alert.setContentText("La compétition a été modifiée avec succès.");
                alert.showAndWait();

                // Fermez la fenêtre de modification
                modifierC.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            }
        }
    }


}
