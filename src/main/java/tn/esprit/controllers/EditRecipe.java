package tn.esprit.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Food;
import tn.esprit.models.Recipe;
import tn.esprit.services.FoodService;
import tn.esprit.services.RecipeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditRecipe {
    @FXML
    private TextField nameField;
    @FXML
    private ListView<Food> foodListView;

    private Recipe currentRecipe;
    private RecipeService recipeService = new RecipeService();
    private FoodService foodService = new FoodService();

    // Define selectedFoods as a class member variable
    private ObservableList<Food> selectedFoods = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            List<Food> foods = foodService.recuperer();
            ObservableList<Food> observableFoods = FXCollections.observableArrayList(foods);

            foodListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Food, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(Food item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            selectedFoods.add(item); // Add the item to the list if it's selected
                        } else {
                            selectedFoods.remove(item); // Remove the item from the list if it's deselected
                        }
                    });
                    return observable;
                }
            }));
            foodListView.setItems(observableFoods);
        } catch (SQLException e) {
            System.err.println("Failed to load foods: " + e.getMessage());
        }
    }

    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        nameField.setText(recipe.getName());
        for (Food food : recipe.getFoods()) {
            foodListView.getSelectionModel().select(food);
        }
    }

    public void saveChanges(ActionEvent actionEvent) {
        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this recipe?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.YES) {
            currentRecipe.setName(nameField.getText());
            if (currentRecipe.getName().length() < 2) {
                showAlert("Name must be at least 2 letters", Alert.AlertType.ERROR);
                return;
            }
            // Update the foods list of the currentRecipe object
            currentRecipe.getFoods().clear();
            currentRecipe.getFoods().addAll(selectedFoods);
            if (selectedFoods.isEmpty()) {
                showAlert("Please select at least one food.", Alert.AlertType.WARNING);
                return;
            }

            // Recalculate the total calories, total fat, total carbs, and total protein
            int totalCalories = 0;
            int totalFat = 0;
            int totalCarbs = 0;
            int totalProtein = 0;
            for (Food food : currentRecipe.getFoods()) {
                totalCalories += food.getCalories();
                totalFat += food.getFat();
                totalCarbs += food.getCarbohydrates();
                totalProtein += food.getProtein();
            }
            currentRecipe.setTotalCalories(totalCalories);
            currentRecipe.setTotalFat(totalFat);
            currentRecipe.setTotalCarbs(totalCarbs);
            currentRecipe.setTotalProtein(totalProtein);

            try {
                recipeService.modifier(currentRecipe);
                // Update the ListView
                foodListView.setItems(selectedFoods);

                // Create a success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Recipe edited successfully!", ButtonType.OK);
                successAlert.showAndWait();

                // Navigate to the viewRecipe.fxml page
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewRecipe.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (SQLException | IOException e) {
                System.err.println("Failed to update the recipe: " + e.getMessage());
            }
        }
    }

    public void addRecipe(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRecipe.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    public void viewRecipe(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewRecipe.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    public void foodNavigate(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewFood.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}