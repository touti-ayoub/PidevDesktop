package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import tn.esprit.models.Participation;
import tn.esprit.services.ParticipationService;

import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class afficherDemande {
    @FXML
    private Label codeP;

    @FXML
    private Label codeU;

    @FXML
    private Label codeC;

    @FXML
    private Label desc;

    @FXML
    private Button accepterBtn;

    @FXML
    private Button refuserBtn;
    @FXML
    private Button retour;

    private Participation selectedParticipation; // Declare selectedParticipation here

    public void initData(Participation participation) {
        if (participation != null) {
            selectedParticipation = participation;
            codeP.setText("Participation: " + participation.getCodeP());
            codeU.setText("Participant: " + participation.getCodeU());
            codeC.setText("Compétition: " + participation.getCodeC());
            desc.setText("Description: " + participation.getDescription());
        } else {
            // Gérez le cas où participation est null (affichage d'un message d'erreur, log, etc.)
            System.err.println("Erreur: La participation est null dans initData.");
        }
    }


    // Link the "refuser" button to this method
    @FXML
    private void handleRefuserButtonClick() {
        if (selectedParticipation != null) {
            ParticipationService participationService = new ParticipationService();
            try {
                // Modifier l'état de la demande et obtenir le résultat
                boolean success = participationService.modifierEtatDemande(selectedParticipation.getCodeP(), 1);

                if (success) {
                    // Afficher un message de confirmation
                    showAlert("Demande réfusée avec succès!");
                    // Actualiser l'interface ou effectuer d'autres actions si nécessaire
                } else {
                    showAlert("Erreur lors de réfuse de la demande.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception de manière appropriée
                showAlert("Une erreur s'est produite. Veuillez réessayer.");
            }
        }
    }


    @FXML
    private void handleAccepterButtonClick() {
        if (selectedParticipation != null) {
            ParticipationService participationService = new ParticipationService();
            try {
                // Modifier l'état de la demande et obtenir le résultat
                boolean success = participationService.modifierEtatDemande(selectedParticipation.getCodeP(), 2);

                if (success) {
                    // Afficher un message de confirmation
                    showAlert("Demande acceptée avec succès!");
                    // Actualiser l'interface ou effectuer d'autres actions si nécessaire
                } else {
                    showAlert("Erreur lors de l'acceptation de la demande.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception de manière appropriée
                showAlert("Une erreur s'est produite. Veuillez réessayer.");
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        loadListeDemandeInterface();
    }


    @FXML
    private void handleRetourButtonClick(ActionEvent event) {
        loadListeDemandeInterface();
    }

    private void loadListeDemandeInterface() {
        try {
            // Load the FXML file for listeParticipation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeDemande.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the current stage from the Retour button
            Stage currentStage = (Stage) retour.getScene().getWindow();

            // Set the new scene in the existing stage
            currentStage.setScene(scene);

            // Show the existing stage (listeParticipation)
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (display an error message, log it, etc.)
        }
    }



} 
