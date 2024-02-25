package tn.esprit.controllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.CompetitionService;
import tn.esprit.services.ParticipationService;
import tn.esprit.services.UtilisateurService;

import java.sql.SQLException;
import java.util.List;

public class modifierParticipation {
    @FXML
    private TextArea Description;

    @FXML
    private ComboBox<Competition> listCompetition;

    @FXML
    private ComboBox<Utilisateur> listUtilisateur;

    @FXML
    private Button modifierP;
    private Participation participation;

    private ParticipationService participationService; // Ajoutez le service
    private final CompetitionService competitionService = new CompetitionService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final ParticipationService ps = new ParticipationService();
    @FXML
    public void initialize() {
        try {
            // Charger la liste des compétitions depuis la base de données
            List<Competition> competitions = competitionService.recuperer();
            listCompetition.getItems().addAll(competitions);

            // Charger la liste des participations depuis la base de données
            List<Utilisateur> utilisateurs = utilisateurService.recuperer();
            listUtilisateur.getItems().addAll( utilisateurs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public modifierParticipation() {
        // Initialiser votre service ici
        this.participationService = new ParticipationService();
    }
    public void initData(Participation participation) {
        // Initialisez les champs avec les informations de la compétition
        Description.setText(participation.getDescription());

    }

    @FXML
    private void handleModifierButtonClick(ActionEvent event) {
        // Vérifiez si la compétition a été initialisée
        if (participation != null) {
            try {
                // Mise à jour de la compétition en utilisant le service
                participationService.modifier(participation);

                // Affichez une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setHeaderText(null);
                alert.setContentText("La compétition a été modifiée avec succès.");
                alert.showAndWait();

                // Fermez la fenêtre de modification
                modifierP.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            }
        }
    }

}
