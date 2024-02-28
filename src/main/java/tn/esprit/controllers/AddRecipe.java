package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.models.Recipe;
import tn.esprit.services.FoodService;
import tn.esprit.services.RecipeService;

import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddRecipe {

    @FXML
    private TextField nameField;

    @FXML
    private ListView<Food> foodListView;

    @FXML
    private Text actionStatus;

    private RecipeService recipeService;
    private FoodService foodService;

    // Define selectedFoods as a class member variable
    private ObservableList<Food> selectedFoods = FXCollections.observableArrayList();



    public AddRecipe() {
        recipeService = new RecipeService();
        foodService = new FoodService();
    }

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
                        System.out.println("Check box for " + item.getName() + " changed from " + wasSelected + " to " + isNowSelected);
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
            actionStatus.setText("Failed to load foods: " + e.getMessage());
        }
    }

    @FXML
    protected void addRecipe(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRecipe.fxml"));

            // Create the scene
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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

    public void editRecipe(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editRecipe.fxml"));

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

    public void deleteRecipe(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/deleteRecipe.fxml"));

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

    @FXML
    public void inesrtRecipe(ActionEvent actionEvent) {
        String name = nameField.getText();

        if (selectedFoods.isEmpty()) {
            actionStatus.setText("Please select at least one food.");
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.getFoods().addAll(selectedFoods);

        try {
            recipeService.ajouter(recipe);
            actionStatus.setText("Recipe added successfully!");
        } catch (SQLException e) {
            actionStatus.setText("Failed to add recipe: " + e.getMessage());
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
}