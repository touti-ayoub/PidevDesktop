package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.models.Participation;
import tn.esprit.services.ParticipationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeDemande implements Initializable {








    @FXML
    private ListView<Participation> participationListView;
    @FXML
    private ListView<Competition> competitionListView;

    @FXML
    private Label lienAjoutP;
    private static listeDemande instance;
    public static listeDemande getInstance() {
        return instance;
    }

    @FXML
    private Label lienC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
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
            if (!participationListView.getItems().isEmpty()) {

                Participation selectedParticipation = participationListView.getSelectionModel().getSelectedItem();
                moveToAnotherInterface(selectedParticipation);
            }else {
                // Affichez un message ou effectuez une action appropriée lorsque le ListView est vide
                // Affichez un message sur l'interface lorsque le ListView est vide
                showEmptyListViewMessage();            }
        }
    }
    private void showEmptyListViewMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("La Liste des participation est vide.");

        // Affichez le message sur l'interface
        alert.showAndWait();
    }
    private void moveToAnotherInterface(Participation participation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherDemande.fxml"));
            Parent root = loader.load();

            afficherDemande controller = loader.getController();
            controller.initData(participation);  // Ajoutez cette ligne pour initialiser les données

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
    @FXML
    private void handleCompetitionLabelClick(ActionEvent event) {
        try {
            // Chargez une nouvelle scène avec le fichier FXML de l'interface d'ajout de compétition
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeCompetition.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène et configurez-la dans votre stage principal
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Montrer la nouvelle scène
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
        }
    }

    @FXML
    private void handleAjouterParticipationClick(ActionEvent event) {
        try {
            // Chargez une nouvelle scène avec le fichier FXML de l'interface d'ajout de compétition
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterCompetition.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène et configurez-la dans votre stage principal
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Montrer la nouvelle scène
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
        }
    }


}
