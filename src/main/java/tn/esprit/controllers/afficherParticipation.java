package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;

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
    //private Participation selectedParticipation; // Déclarer selectedCompetition ici

    public void initData(Participation participation) {
    //selectedParticipation = participation;
        codeP.setText("Participation: " + participation.getCodeP());
    codeU.setText("Participant: " + participation.getCodeU());
    codeC.setText("Compétition: " + participation.getCodeC());
    desc.setText("Description: " + participation.getDescription());
}
   /* @FXML
    private void handleModifierButtonClick(ActionEvent event) {
        // Utilisez la compétition actuellement affichée
        if (selectedParticipation != null) {
            loadModifierCompetitionInterface(selectedParticipation);
        }
    }
    private void loadModifierCompetitionInterface(Competition competition) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierParticipation.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la nouvelle interface et transmettez les données
            modifierParticioation controller = loader.getController();
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
    }*/

}
