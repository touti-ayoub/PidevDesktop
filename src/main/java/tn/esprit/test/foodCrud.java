package tn.esprit.test;

import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.sql.SQLException;

public class foodCrud {

    public static void main(String[] args) {
        FoodService rs= new FoodService();

        // ------------------------------ ADD ------------------------------
        /*
        try {
            rs.ajouter(new Food("test1",12,15,200,45,12.2,"gram"));
            rs.ajouter(new Food("test2",2,156,2700,455,1132.2,"litre"));
            rs.ajouter(new Food("test3",152,125,200,5,122.2,"cup"));
            System.out.println("ADD DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */
        // ------------------------------ MODIFY ------------------------------
        /*
        try {
            rs.modifier(new Food(2,"test50",12,15,200,45,12.2,"gram"));
            System.out.println("MODIFY DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */
        // ------------------------------ DELETE ------------------------------
        /*
        try {
            rs.supprimer(2);
            System.out.println("DELETE DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */
        // ------------------------------ READ ------------------------------

        try {
            System.out.println(rs.recuperer());
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
