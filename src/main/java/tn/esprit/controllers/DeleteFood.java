package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeleteFood {

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
    void delete(ActionEvent event) {
        String selectedFoodName = foodComboBox.getValue();
        try {
            Food foodToDelete = foodService.filterByName(selectedFoodName).get(0);

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setContentText("Are you sure you want to delete " + selectedFoodName + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                boolean isDeleted = foodService.supprimer(foodToDelete.getId());
                Alert alert;
                if (isDeleted) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("DONE");
                    alert.setContentText("Food Deleted Successfully");
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO");
                    alert.setContentText("Food name not found in the database, no deletion was performed");
                }
                alert.showAndWait();
            } else {
                // User chose CANCEL or closed the dialog
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR !!!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR !!!");
            alert.setContentText("Food name not found in the database, no deletion was performed");
            alert.showAndWait();
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

    public void recipeNavigate(ActionEvent actionEvent) {
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

    @FXML
    private ComboBox<String> foodComboBox;

    private FoodService foodService = new FoodService();

    @FXML
    public void initialize() {
        try {
            fillComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillComboBox() throws SQLException {
        List<Food> foods = foodService.recuperer();
        for (Food food : foods) {
            foodComboBox.getItems().add(food.getName());
        }
    }
}
