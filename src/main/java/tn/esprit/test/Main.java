package tn.esprit.test;

import tn.esprit.models.Repas;
import tn.esprit.services.RepasService;
import tn.esprit.utils.MyDatabase;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        RepasService rs= new RepasService();

        // ------------------------------ ADD ------------------------------
        /*
        try {
            rs.ajouter(new Repas(100,"escalope"));
            rs.ajouter(new Repas(20,"ouef"));
            rs.ajouter(new Repas(50,"tomate"));
            System.out.println("ADD DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

        // ------------------------------ MODIFY ------------------------------
        /*
        try {
            rs.modifier(new Repas(1,20,"oeuf"));
            System.out.println("MODIFY DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } */

        // ------------------------------ DELETE ------------------------------
        /*
        try {
            rs.supprimer(1);
            System.out.println("DELETE DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

        // ------------------------------ READ ------------------------------
        try {
            System.out.println(rs.recuperer());
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
