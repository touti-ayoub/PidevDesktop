package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tn.esprit.models.Food;
import tn.esprit.models.Recipe;
import tn.esprit.services.FoodService;
import tn.esprit.services.RecipeService;

import java.sql.SQLException;
import java.util.List;

public class AddRecipe {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Food> foodComboBox;

    @FXML
    private Text actionStatus;

    private RecipeService recipeService;
    private FoodService foodService;

    public AddRecipe() {
        recipeService = new RecipeService();
        foodService = new FoodService();
    }

    @FXML
    public void initialize() {
        try {
            List<Food> foods = foodService.recuperer();
            ObservableList<Food> observableFoods = FXCollections.observableArrayList(foods);
            foodComboBox.setItems(observableFoods);
        } catch (SQLException e) {
            actionStatus.setText("Failed to load foods: " + e.getMessage());
        }
    }

    @FXML
    protected void addRecipe(ActionEvent event) {
        String name = nameField.getText();
        Food selectedFood = foodComboBox.getSelectionModel().getSelectedItem();

        if (selectedFood == null) {
            actionStatus.setText("Please select a food.");
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.getFoods().add(selectedFood);

        try {
            recipeService.ajouter(recipe);
            actionStatus.setText("Recipe added successfully!");
        } catch (SQLException e) {
            actionStatus.setText("Failed to add recipe: " + e.getMessage());
        }
    }


}