package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.services.CompetitionService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ajouterCompetition {
    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TextField libelle;

    @FXML
    private TextField tarifTxt;

  //  @FXML
   // private TextField nbrMembre;
private final CompetitionService cs = new CompetitionService();

    @FXML
    void ajouterCompetition(ActionEvent event) throws SQLException, ParseException {
        if (dateDebut.getValue() == null || dateFin.getValue() == null || libelle.getText().isEmpty() || tarifTxt.getText().isEmpty()) {
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

                // Conversion de la chaîne nombre en int
           //     int nbrMaxMembresValue = Integer.parseInt(nbrMaxMembres.getText());
float tarif =Float.parseFloat(tarifTxt.getText());
                // Ajouter la compétition avec les dates converties
                cs.ajouter(new Competition(libelle.getText(), dateDebutValue, dateFinValue, tarif));
                // Après avoir ajouté la compétition avec succès, naviguez vers la liste des compétitions
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeCompetition.fxml"));
                Parent root = loader.load();

                // Créez une nouvelle scène et configurez-la dans votre stage principal
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                // Obtenez l'instance de listeCompetition
                listeCompetition controllerListe = loader.getController();

                // Appelez la méthode de rafraîchissement sur l'instance de listeCompetition
                controllerListe.refreshListeCompetitions();

                // Montrez la nouvelle scène
                stage.show();

                // Fermez la scène actuelle (ajouterCompetition)
                Stage currentStage = (Stage) libelle.getScene().getWindow();
                currentStage.close();
            } catch (NumberFormatException | IOException e) {
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
