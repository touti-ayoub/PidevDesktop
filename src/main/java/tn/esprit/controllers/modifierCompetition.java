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

    @FXML
    private Button annuler;

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
            // Ajoutez des vérifications pour les champs similaires à la méthode ajouterCompetition

            if (dateDebut.getValue() == null || dateFin.getValue() == null || libelle.getText().isEmpty() || nbr.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de saisie !");
                alert.setContentText("Veuillez remplir tous les champs, y compris la date.");
                alert.show();
            } else {
                try {
                    // Conversion des objets LocalDate en java.util.Date
                    java.sql.Date dateDebutValue = java.sql.Date.valueOf(dateDebut.getValue());
                    java.sql.Date dateFinValue = java.sql.Date.valueOf(dateFin.getValue());

                    // Vérifier si la date de début est supérieure à la date système
                    if (dateDebutValue.before(new java.util.Date())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Date de début invalide");
                        alert.setContentText("La date de début ne peut pas être antérieure à la date actuelle.");
                        alert.show();
                        return; // Arrêter l'exécution si la date de début est invalide
                    }

                    // Vérifier si la date de fin est supérieure à la date de début
                    if (dateFinValue.before(dateDebutValue)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Date de fin invalide");
                        alert.setContentText("La date de fin ne peut pas être antérieure à la date de début.");
                        alert.show();
                        return; // Arrêter l'exécution si la date de fin est invalide
                    }

                    // Conversion de la chaîne nombre en float
                    float tarif = Float.parseFloat(nbr.getText());

                    // Mettez à jour les champs de la compétition avec les nouvelles valeurs entrées par l'utilisateur
                    competition.setLibelle(libelle.getText());
                    competition.setDateDebut(dateDebutValue);
                    competition.setDateFin(dateFinValue);
                    competition.setTarif(tarif);

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

                } catch (NumberFormatException | SQLException e) {
                    // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur de conversion du nombre");
                    alert.setContentText("Veuillez saisir un nombre valide pour le tarif ");
                    alert.show();
                }
            }
        }
    }
    @FXML
    private void handleAnnulerButtonClick(ActionEvent event) {
        // Close the modifierCompetition window
        annuler.getScene().getWindow().hide();

        // If you need to refresh the afficherCompetition interface, you can call the refresh method here
        if (afficherCompetition != null) {
            afficherCompetition.refreshCompetitions();
        }
    }

}
