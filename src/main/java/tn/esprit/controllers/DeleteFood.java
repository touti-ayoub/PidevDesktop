package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteFood {
    @FXML
    private TextField idTF;

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
        FoodService foodService = new FoodService();
        int id = Integer.parseInt(idTF.getText());

        try {
            foodService.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DONE");
            alert.setContentText("Food Deleted Successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR !!!!!!");
            alert.setContentText(e.getMessage());
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
}
