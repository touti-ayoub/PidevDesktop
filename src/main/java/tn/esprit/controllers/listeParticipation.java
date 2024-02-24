package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.services.CompetitionService;
import tn.esprit.services.ParticipationService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeParticipation implements Initializable {
    @FXML
    private ListView<Participation> participationListView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ParticipationService ps = new ParticipationService();
        try {
            List<Participation> participations = ps.recuperer();
            ObservableList<Participation> participationObservableList = FXCollections.observableArrayList(participations);
            participationListView.setItems(participationObservableList);
            //ObservableList pour rendre votre ListView réactif aux changements dans la liste des compétitions
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            e.printStackTrace();
        }
    }


    private static class ParticipationListCell extends ListCell<Participation> {
        @Override
        protected void updateItem(Participation participation, boolean empty) {
            super.updateItem(participation, empty);

            if (empty || participation == null) {
                setText(null);
            } else {
                // Afficher les attributs de la participation dans la cellule
                setText("Description: " + participation.getDescription());
            }
        }
    }
}
