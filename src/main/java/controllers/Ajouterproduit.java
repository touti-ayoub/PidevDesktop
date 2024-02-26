package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Produit;
import tn.esprit.service.ProduitService;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Ajouterproduit {
    @FXML
    private TextField idTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField tailleTF;

    @FXML
    private TextField typeTF;

    public Ajouterproduit() {
    }

    @FXML
    void afficherProduit(ActionEvent event) {

    }

    @FXML
    void ajouterProduit(ActionEvent event) {
ProduitService produitService = new produitService();
    Produit produit = new Produit();
    produit.setId(idTF.getText());
    produit.setNom(nomTF.getText());
    produit.setTaille(tailleTF.getText());
    produit.setType(typeTF.getText());
        try {
            produitService.ajouter(produit);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("success");
            alert.setContentText("produit ajouté");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
