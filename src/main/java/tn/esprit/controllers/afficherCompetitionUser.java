package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ParticipationService;
import tn.esprit.services.UtilisateurService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class afficherCompetitionUser implements Initializable {

    @FXML
    private ComboBox<Utilisateur> comboUser;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label libelleLabel;

    @FXML
    private Label tarifLabel;

    @FXML
    private Button demanderParticipation;

    @FXML
    private Button retour;

    private Competition selectedCompetition;

    public void initData(Competition competition) {
        selectedCompetition = competition;

        if (competition != null) {
            libelleLabel.setText("Compétition: " + (competition.getLibelle() != null ? competition.getLibelle() : ""));
            dateDebutLabel.setText("Date de début: " + (competition.getDateDebut() != null ? competition.getDateDebut() : ""));

            // Convert float to String before setting it to the label
            tarifLabel.setText("Tarif: " + (competition.getTarif() != 0.0f ? String.valueOf(competition.getTarif()) : "N/A"));

            if (competition.getDateFin() != null) {
                dateFinLabel.setText("Date de fin: " + competition.getDateFin().toString());
            } else {
                dateFinLabel.setText("Date de fin: N/A");
            }
        } else {
            libelleLabel.setText("Compétition: N/A");
            dateDebutLabel.setText("Date de début: N/A");
            dateFinLabel.setText("Date de fin: N/A");
            tarifLabel.setText("Tarif: N/A");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the list of users into the ComboBox
        try {
            UtilisateurService utilisateurService = new UtilisateurService();
            List<Utilisateur> utilisateurs = utilisateurService.recuperer();
            comboUser.setItems(FXCollections.observableArrayList(utilisateurs));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (display an error message, logging, etc.)
        }
    }

    @FXML
    private void handleDemanderParticipationButtonClick(ActionEvent event) {
        // Get the selected competition and user
        Utilisateur selectedUser = comboUser.getValue();

        // Create a Participation instance with the selected competition, user, and other details
        Participation participation = new Participation(selectedUser.getCodeU(), selectedCompetition.getCodeC(), "Your Description Here");

        try {
            ParticipationService ps = new ParticipationService();

            // Call the ajouterParticipation method in ParticipationService
            ps.ajouterParticipation(participation);

            // Show a message dialog with information about the added participation
            showMessageDialog("Participation Added", "Participation added successfully!\nDetails:\n" + selectedUser + "\n" + selectedCompetition+" tarif aprés réduction"+participation.getTarifApresReduit());

            // Close the current scene
            Stage stage = (Stage) demanderParticipation.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Method to show a message dialog
    private void showMessageDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleRetourButtonClick(ActionEvent event) {
        try {
            // Load the CalendrierCompetition interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalendrierCompetition.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it in your main stage
            Scene scene = new Scene(root);
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}
