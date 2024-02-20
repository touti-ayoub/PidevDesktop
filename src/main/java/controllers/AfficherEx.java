package controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;

import java.sql.SQLException;
import java.util.List;

public class AfficherEx {
    @FXML
    private TableColumn<exercice, String> descriptionEx;

    @FXML
    private TableColumn<exercice, String> muscleEX;

    @FXML
    private TableColumn<exercice, String> nomEx;

    @FXML
    private TableView<exercice> tableView;

    private final exerciceService es = new exerciceService();

    @FXML
    void initialize() {


        try {
            List<exercice> exercices = es.recuperer();
            ObservableList<exercice> observableList = FXCollections.observableList(exercices);
            tableView.setItems(observableList);
            nomEx.setCellValueFactory(new PropertyValueFactory<>("NOM"));
            descriptionEx.setCellValueFactory((new PropertyValueFactory<>("DESCRIPTION")));
            muscleEX.setCellValueFactory((new PropertyValueFactory<>("MUSCLE_CIBLE")));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
