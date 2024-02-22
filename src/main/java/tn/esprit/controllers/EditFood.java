package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;

public class EditFood {
    @FXML
    private TextField caloriesTF;

    @FXML
    private TextField carbsTF;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private TextField fatTF;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField proteinTF;

    @FXML
    private TextField servsizeTF;

    @FXML
    void Select(ActionEvent event) {

    }

    @FXML
    void addFood(ActionEvent event) {

    }

    @FXML
    void deleteFood(ActionEvent event) {

    }

    @FXML
    void editFood(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {
        try {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this food?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait();

            if (confirmationAlert.getResult() == ButtonType.YES) {
                // If the user clicks YES, proceed with the editing

                // Create a new FoodService
                FoodService foodService = new FoodService();

                // Create the updated food
                Food updatedFood = new Food();
                updatedFood.setId(Integer.parseInt(idTF.getText()));
                updatedFood.setName(nameTF.getText());
                updatedFood.setCalories(Integer.parseInt(caloriesTF.getText()));
                updatedFood.setProtein(Integer.parseInt(proteinTF.getText()));
                updatedFood.setCarbohydrates(Integer.parseInt(carbsTF.getText()));
                updatedFood.setFat(Integer.parseInt(fatTF.getText()));
                updatedFood.setServingSize(Double.parseDouble(servsizeTF.getText()));
                updatedFood.setServingUnit(combo.getValue());

                // Update the food
                int rowsAffected = foodService.modifier(updatedFood);

                if (rowsAffected > 0) {
                    // If the food was successfully updated, show a success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Food successfully updated!");
                    successAlert.showAndWait();
                } else {
                    // If the food was not found in the database, show an error message
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Food with this ID was not found in the database!");
                    errorAlert.showAndWait();
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to update the food: " + e.getMessage());
        }
    }

    @FXML
    void viewFood(ActionEvent event) {

    }

    public void initialize() {
        combo.setItems(FXCollections.observableArrayList("gram","ounce","cup","litre"));
    }
}