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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.services.CompetitionService;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeCompetition implements Initializable {

    @FXML
    private ListView<Competition> competitionListView;
    private static listeCompetition instance;
    public static listeCompetition getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        CompetitionService cs = new CompetitionService();
        try {
            List<Competition> competitions = cs.recuperer();
            ObservableList<Competition> competitionObservableList = FXCollections.observableArrayList(competitions);
            competitionListView.setItems(competitionObservableList);

            // Ajoutez un gestionnaire d'événements pour traiter les clics sur les éléments de la ListView
            competitionListView.setOnMouseClicked(event -> handleListViewClick(event));

        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (affichage d'un message d'erreur, journalisation, etc.)
            e.printStackTrace();
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
        try {
            // Chargez une nouvelle scène avec le fichier FXML de l'interface suivante
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherCompetition.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la nouvelle interface et transmettez les données
            afficherCompetition controller = loader.getController();
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

        }

    public void refreshListeCompetitions() {
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
}