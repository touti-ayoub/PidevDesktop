package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.sql.SQLException;

public class AddFood {

    @FXML
    private TextField caloriesTF;

    @FXML
    private TextField carbsTF;

    @FXML
    private TextField fatTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField proteinTF;

    @FXML
    private TextField servsizeTF;

    @FXML
    private TextField servunitTF;

    @FXML
    void addFood(ActionEvent event) {
        FoodService foodService = new FoodService();
        Food food = new Food();
        food.setName(nameTF.getText());
        food.setCalories(Integer.parseInt(caloriesTF.getText()));
        food.setProtein(Integer.parseInt(proteinTF.getText()));
        food.setCarbohydrates(Integer.parseInt(carbsTF.getText()));
        food.setFat(Integer.parseInt(fatTF.getText()));
        food.setServingSize(Double.parseDouble(servsizeTF.getText()));
        food.setServingUnit(servunitTF.getText());

        try {
            foodService.ajouter(food);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DONE");
            alert.setContentText("Food Added Successfully");
            alert.showAndWait();
        } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR !!!!!!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }

    }

    @FXML
    void displayFood(ActionEvent event) {

    }
}
