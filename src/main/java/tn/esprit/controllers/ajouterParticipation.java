package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.CompetitionService;
import tn.esprit.services.ParticipationService;
import tn.esprit.services.UtilisateurService;

import java.awt.event.ActionEvent; // Cette importation semble inutile
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ajouterParticipation {

    @FXML
    private javafx.scene.control.TextArea description;

    @FXML
    private ComboBox<Competition> listCompetition;

    @FXML
    private ComboBox<Utilisateur> listUtilisateur;

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
            listUtilisateur.getItems().addAll(utilisateurs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML


    public void ajouterParticipation(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
            // Récupérer la compétition sélectionnée
            Competition competitionSelectionnee = listCompetition.getValue();

            // Récupérer l'utilisateur sélectionné
            Utilisateur utilisateurSelectionne = listUtilisateur.getValue();

            // Récupérer la description depuis la TextArea
            String descriptionParticipation = description.getText();

            if (competitionSelectionnee == null || utilisateurSelectionne == null || descriptionParticipation.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de saisie !");
                alert.setContentText("Veuillez sélectionner une compétition, un utilisateur et saisir une description.");
                alert.show();
            } else {
                // Créer et ajouter la participation
                ps.ajouterParticipation(new Participation(competitionSelectionnee.getCodeC(), descriptionParticipation ,utilisateurSelectionne.getCodeU()));
                // Après avoir ajouté la participation avec succès, naviguez vers la liste des participations
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeParticipation.fxml"));
                Parent root = loader.load();

                // Obtenez le contrôleur de la nouvelle interface et transmettez les données
                listeParticipation controllerListeParticipation = loader.getController();

                // Appelez la méthode de rafraîchissement sur l'instance de listeParticipation
                controllerListeParticipation.refreshListeParticipations();

                // Créez une nouvelle scène et configurez-la dans votre stage principal
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                // Montrez la nouvelle scène
                stage.show();

                // Fermez la scène actuelle (ajouterParticipation)
                Stage currentStage = (Stage) listCompetition.getScene().getWindow();
                currentStage.close();
                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Participation ajoutée avec succès");
                alert.setContentText("La participation a été ajoutée avec succès.");
                alert.show();
            }
        }

        }


