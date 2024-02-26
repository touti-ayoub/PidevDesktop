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
    private CompetitionService competitionService=new CompetitionService(); // Ajoutez le service



    public void initData(Competition competition) {
        this.competition = competition;

        // Initialisez les champs avec les informations de la compétition
        libelle.setText(competition.getLibelle());
        dateDebut.setValue(competition.getDateDebut().toLocalDate());
        dateFin.setValue(competition.getDateFin().toLocalDate());
        nbr.setText(String.valueOf(competition.getTarif()));
    }

    private afficherCompetition afficherCompetition;

    // Add a method to set the reference to afficherCompetition
    public void setAfficherCompetition(afficherCompetition afficherCompetition) {
        this.afficherCompetition = afficherCompetition;
    }

    @FXML
    private void handleModifierButtonClick(ActionEvent event) {
        // Vérifiez si la compétition a été initialisée
        if (competition != null) {
            try {
                // Mettez à jour les champs de la compétition avec les nouvelles valeurs entrées par l'utilisateur
                competition.setLibelle(libelle.getText());
                competition.setDateDebut(java.sql.Date.valueOf(dateDebut.getValue()));
                competition.setDateFin(java.sql.Date.valueOf(dateFin.getValue()));
                competition.setTarif(Float.parseFloat(nbr.getText()));

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
// Rafraîchissez la liste dans le contrôleur listeCompetition
                if (afficherCompetition != null) {
                    afficherCompetition.refreshCompetitions();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("SQL Exception: " + e.getMessage());
            }
        }
    }

}
