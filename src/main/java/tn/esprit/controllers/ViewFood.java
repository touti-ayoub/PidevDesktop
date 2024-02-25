package tn.esprit.controllers;

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
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewFood {
    @FXML
    private TableColumn<Food, Integer> calF;

    @FXML
    private TableColumn<Food, Integer> protF;

    @FXML
    private TableColumn<Food, Integer> carbsF;

    @FXML
    private TableColumn<Food, Integer> fatF;

    @FXML
    private TableColumn<Food, String> nameF;

    @FXML
    private TableColumn<Food, Integer> sizeF;

    @FXML
    private TableView<Food> tableF;

    @FXML
    private TableColumn<?, ?> unitF;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainRecipe.fxml"));

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
    void initialize() {
        FoodService foodService = new FoodService();
        try {
            List<Food> food = foodService.recuperer();
            ObservableList<Food> observableList = FXCollections.observableList(food);
            tableF.setItems(observableList);
            nameF.setCellValueFactory(new PropertyValueFactory<>("Name"));
            calF.setCellValueFactory(new PropertyValueFactory<>("calories"));
            protF.setCellValueFactory(new PropertyValueFactory<>("protein"));
            carbsF.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
            fatF.setCellValueFactory(new PropertyValueFactory<>("fat"));
            sizeF.setCellValueFactory(new PropertyValueFactory<>("servingSize"));
            unitF.setCellValueFactory(new PropertyValueFactory<>("servingUnit"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
