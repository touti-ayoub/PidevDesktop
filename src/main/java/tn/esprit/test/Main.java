package tn.esprit.test;

import tn.esprit.models.Produit;
import tn.esprit.service.ProduitService;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ProduitService ps = new ProduitService();

        try {
            ps.ajouter(new Produit("proteine", "liquide", "small"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}