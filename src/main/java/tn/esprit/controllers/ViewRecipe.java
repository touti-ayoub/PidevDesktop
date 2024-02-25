package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.models.Recipe;
import tn.esprit.services.RecipeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewRecipe {
    @FXML
    private TableColumn<Recipe, String> nameR;

    @FXML
    private TableColumn<Recipe, Integer> calR;

    @FXML
    private TableColumn<Recipe, Integer> protR;

    @FXML
    private TableColumn<Recipe, Integer> carbsR;

    @FXML
    private TableColumn<Recipe, Integer> fatR;

    @FXML
    private TableView<Recipe> tableR;

    @FXML
    private TableColumn<Recipe, List<Food>> foodsR;

    @FXML
    void addRecipe(ActionEvent event) {
        changeScene(event, "/addRecipe.fxml");
    }

    @FXML
    void deleteRecipe(ActionEvent event) {
        changeScene(event, "/deleteRecipe.fxml");
    }

    @FXML
    void editRecipe(ActionEvent event) {
        changeScene(event, "/editRecipe.fxml");
    }

    @FXML
    void viewRecipe(ActionEvent event) {
        changeScene(event, "/main.fxml");
    }

    @FXML
    void initialize() {
        RecipeService recipeService = new RecipeService();
        try {
            List<Recipe> recipes = recipeService.recuperer();
            ObservableList<Recipe> observableList = FXCollections.observableList(recipes);
            tableR.setItems(observableList);
            nameR.setCellValueFactory(new PropertyValueFactory<>("name"));
            calR.setCellValueFactory(new PropertyValueFactory<>("totalCalories"));
            protR.setCellValueFactory(new PropertyValueFactory<>("totalProtein"));
            carbsR.setCellValueFactory(new PropertyValueFactory<>("totalCarbs"));
            fatR.setCellValueFactory(new PropertyValueFactory<>("totalFat"));

            // Create a new TableColumn for the food names
            TableColumn<Recipe, String> foodsColumn = new TableColumn<>("Foods");
            foodsColumn.setCellValueFactory(cellData -> {
                List<Food> foods = cellData.getValue().getFoods();
                if (foods != null) {
                    String foodNames = foods.stream()
                            .map(Food::getName)
                            .collect(Collectors.joining(", "));
                    return new SimpleStringProperty(foodNames);
                } else {
                    return new SimpleStringProperty("");
                }
            });

            // Add the new column to the TableView
            tableR.getColumns().add(foodsColumn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void changeScene(ActionEvent event, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    @FXML
    void addFood(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addFood.fxml"));

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

    public void foodNavigate(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));

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