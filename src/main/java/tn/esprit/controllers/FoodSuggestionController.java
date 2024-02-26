package tn.esprit.controllers;

import javafx.collections.FXCollections;
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
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FoodSuggestionController {

    @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, Integer> caloriesColumn;

    @FXML
    private TableColumn<Food, Integer> proteinColumn;

    @FXML
    private TableColumn<Food, Integer> carbohydratesColumn;

    @FXML
    private TableColumn<Food, Integer> fatColumn;

    @FXML
    private TableColumn<Food, Double> servingSizeColumn;

    @FXML
    private TableColumn<Food, String> servingUnitColumn;

    private FoodService foodService;

    public FoodSuggestionController() {
        foodService = new FoodService();
    }

    @FXML
    public void initialize() {
        foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        carbohydratesColumn.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        servingSizeColumn.setCellValueFactory(new PropertyValueFactory<>("servingSize"));
        servingUnitColumn.setCellValueFactory(new PropertyValueFactory<>("servingUnit"));
        loadFoodSuggestions();
    }

    private void loadFoodSuggestions() {
        try {
            List<Food> foods = foodService.getFoodSuggestions();
            foodTable.setItems(FXCollections.observableArrayList(foods));
        } catch (SQLException e) {
            e.printStackTrace();
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

    @FXML
    void deleteFood(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/deleteFood.fxml"));

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

    @FXML
    void editFood(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editFood.fxml"));

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

    @FXML
    void viewFood(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewFood.fxml"));

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

    @FXML
    void recipeNavigate(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewRecipe.fxml"));

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

    public void bminavigate(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bmi.fxml"));

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