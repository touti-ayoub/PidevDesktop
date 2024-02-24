package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.models.Competition;
import tn.esprit.services.CompetitionService;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeCompetition implements Initializable {

    @FXML
    private ListView<Competition> competitionListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CompetitionService cs = new CompetitionService();
        try {
            List<Competition> competitions = cs.recuperer();
            ObservableList<Competition> competitionObservableList = FXCollections.observableArrayList(competitions);
            competitionListView.setItems(competitionObservableList);
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            e.printStackTrace();
        }
    }
    private static class CompetitionListCell extends ListCell<Competition> {
        @Override
        protected void updateItem(Competition competition, boolean empty) {
            super.updateItem(competition, empty);

            if (empty || competition == null) {
                setText(null);
            } else {
                // Afficher les attributs de la compétition dans la cellule
                setText("Libellé: " + competition.getLibelle() +
                        " | Date de début: " + competition.getDateDebut() +
                        " | Date de fin: " + competition.getDateFin() +
                   /*     " | Nombre de membres: " + competition.getNbrMembre() */
                        " | Nombre max de membres: " + competition.getNbrMaxMembres());
            }
        }
    }
    private void handleListViewClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Vérifiez si le clic est simple (modifiable selon vos besoins)
            Competition selectedCompetition = competitionListView.getSelectionModel().getSelectedItem();

            // Vous pouvez maintenant utiliser les informations de la compétition sélectionnée
            // Par exemple, déplacez-vous vers une autre interface avec ces informations
            moveToAnotherInterface(selectedCompetition);
        }
    }

    private void moveToAnotherInterface(Competition competition) {
        // Implémentez le code pour passer à une autre interface avec les informations de la compétition
        // Vous pouvez utiliser FXMLLoader pour charger une nouvelle scène ou contrôleur, etc.
    }

}
