package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.service.ProduitService;

import java.sql.SQLException;
import java.util.List;

public class Afficherproduit {
    @FXML
    private TableColumn<produit,String> nomCol;

    @FXML
    private TableColumn<produit, Integer> prixCol;

    @FXML
    private TableColumn<produit, Integer> tailleCol;

    @FXML
    private TableColumn<produit, String> typeCol;

    @FXML
    private TableView<produit> tableView;

    @FXML
    void initialize() {
        ProduitService produitService = new produitService();
        try {
            List<produit> produits = produitService.recuperer();
            ObservableList<produit> observableList = FXCollections.observableList(produits);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            tailleCol.setCellValueFactory(new PropertyValueFactory<>("taille"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        } catch (SQLException e) {
          System.err.println(e.getMessage());
        }

    }
}
