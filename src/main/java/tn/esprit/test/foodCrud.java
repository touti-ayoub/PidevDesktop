package tn.esprit.test;

import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.sql.SQLException;

public class foodCrud {

    public static void main(String[] args) {
        FoodService rs= new FoodService();

        // ------------------------------ ADD ------------------------------

        try {
            rs.ajouter(new Food("Apple", 95, 1, 25, 1, 185, "gram"));
            rs.ajouter(new Food("Banana", 105, 1, 27, 1, 118, "gram"));
            rs.ajouter(new Food("Chicken Breast", 165, 31, 0, 4, 100, "gram"));
            rs.ajouter(new Food("Broccoli", 34, 3, 6, 1, 156, "gram"));
            rs.ajouter(new Food("Brown Rice", 216, 5, 45, 2, 185, "gram"));
            rs.ajouter(new Food("Orange", 62, 1, 15, 0, 131, "gram"));
            rs.ajouter(new Food("Oatmeal", 150, 6, 27, 3, 230, "gram"));
            rs.ajouter(new Food("Chicken Breast", 165, 31, 0, 4, 100, "gram"));
            rs.ajouter(new Food("Egg", 68, 6, 1, 5, 1, "piece"));
            rs.ajouter(new Food("Almond Milk", 15, 1, 1, 2, 240, "milliliter"));
            rs.ajouter(new Food("Brown Rice", 112, 3, 24, 1, 100, "gram"));
            rs.ajouter(new Food("Salmon", 206, 22, 0, 13, 100, "gram"));
            rs.ajouter(new Food("Broccoli", 55, 4, 11, 1, 100, "gram"));
            rs.ajouter(new Food("Beef", 250, 26, 0, 17, 100, "gram"));
            rs.ajouter(new Food("Tofu", 144, 16, 4, 8, 100, "gram"));
            rs.ajouter(new Food("Peanut Butter", 588, 25, 20, 50, 100, "milliliter"));
            rs.ajouter(new Food("Milk", 42, 4, 4, 2, 100, "gram"));
            rs.ajouter(new Food("Yogurt", 59, 4, 5, 4, 100, "gram"));
            rs.ajouter(new Food("Cheese", 402, 25, 1, 33, 100, "gram"));
            System.out.println("ADD DONE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

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
            rs.supprimer(1);
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
