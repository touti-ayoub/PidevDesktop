package tn.esprit.controllers;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.services.ParticipationService;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class afficherParticipation {
    @FXML
    private Label desc;

    @FXML
    private Label codeC;

    @FXML
    private Label codeP;

    @FXML
    private Label codeU;
    @FXML
    private Button modifierP;
    @FXML
    private Button retour;
    @FXML
    private Button supprimer;
    private Participation selectedParticipation; // Déclarer selectedCompetition ici

    public void initData(Participation participation) {
        if (participation != null) {
            selectedParticipation = participation;
            codeP.setText("Participant   : " + participation.getUtilisateur().getPrenom());
            codeU.setText("Compétition   : " + participation.getCompetition().getLibelle());
            codeC.setText("date de déubt : " + participation.getCompetition().getDateDebut());
            desc.setText("date de fin   : " + participation.getCompetition().getDateFin());
        } else {
            // Gérez le cas où participation est null (affichage d'un message d'erreur, log, etc.)
            System.err.println("Erreur: La participation est null dans initData.");
        }
    }

    @FXML
    private void handleModifierButtonClick2(ActionEvent event) {
        // Utilisez la participation actuellement affichée
        if (selectedParticipation != null) {
            loadModifierParticipationInterface(selectedParticipation);
        }
    }


    private void loadModifierParticipationInterface(Participation participation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierParticipation.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la nouvelle interface et transmettez les données
            modifierParticipation controller = loader.getController();
            controller.initData(participation);

            // Ajoutez une référence à afficherCompetition dans le contrôleur modifierCompetition
            controller.setAfficherParticipation(this);

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


    @FXML
    private void handleSupprimerButtonClick(ActionEvent event) {
        // Vérifiez si une participation est sélectionnée
        if (selectedParticipation != null) {
            // Créez une boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer cette participation?");

            // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // L'utilisateur a cliqué sur OK, procédez à la suppression
                    try {
                        // Récupérez l'instance du service ou gestionnaire de données
                        ParticipationService participationService = new ParticipationService();

                        // Appelez votre méthode de suppression depuis votre service ou gestionnaire de données
                        int codeParticipationASupprimer = selectedParticipation.getCodeP();
                        participationService.supprimer(codeParticipationASupprimer);

                        // Obtenez l'instance de listeParticipation
                        listeParticipation controllerListe = listeParticipation.getInstance();

                        // Vérifiez si l'instance est null
                        if (controllerListe != null) {
                            // Appelez la méthode de rafraîchissement sur l'instance de listeParticipation
                            controllerListe.refreshListeParticipations();
                        } else {
                            // Gérez le cas où l'instance est null
                            System.out.println("Erreur: Impossible d'obtenir l'instance de listeParticipation.");
                        }

                        // Fermez la scène actuelle (afficherParticipation)
                        Stage currentStage = (Stage) supprimer.getScene().getWindow();
                        currentStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Gérez l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
                    }
                }
            });
        } else {
            // Affichez un message d'avertissement indiquant qu'aucune participation n'est sélectionnée.
            Alert warning = new Alert(AlertType.WARNING);
            warning.setTitle("Aucune participation sélectionnée");
            warning.setHeaderText(null);
            warning.setContentText("Veuillez sélectionner une participation à supprimer.");
            warning.showAndWait();
        }
    }
    public void refreshParticipations() {
        // Appelez votre méthode initData avec la compétition actuellement affichée
        initData(selectedParticipation);
    }
    @FXML
    private void handleRetourButtonClick(ActionEvent event) {
        loadListeParticipationInterface();
    }

    private void loadListeParticipationInterface() {
        try {
            // Load the FXML file for listeParticipation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeParticipation.fxml"));
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
