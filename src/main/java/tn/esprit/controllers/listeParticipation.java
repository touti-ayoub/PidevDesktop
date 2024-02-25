package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.services.CompetitionService;
import tn.esprit.services.ParticipationService;

import java.io.IOException;
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

            // Ajoutez un gestionnaire d'événements pour traiter les clics sur les éléments de la ListView
            participationListView.setOnMouseClicked(event -> handleListViewClick(event));
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            e.printStackTrace();
        }
    }

    private void handleListViewClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Participation selectedParticipation = participationListView.getSelectionModel().getSelectedItem();
            moveToAnotherInterface(selectedParticipation);
        }
    }

    private void moveToAnotherInterface(Participation participation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherParticipation.fxml"));
            Parent root = loader.load();

            afficherParticipation controller = loader.getController();
            controller.initData(participation);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
        }


    }
    public void refreshListeParticipations() {
        ParticipationService ps = new ParticipationService();
        try {
            List<Participation> participations = ps.recuperer();
            ObservableList<Participation> participationObservableList = FXCollections.observableArrayList(participations);
            participationListView.setItems(participationObservableList);
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            e.printStackTrace();
        }
    }
}
