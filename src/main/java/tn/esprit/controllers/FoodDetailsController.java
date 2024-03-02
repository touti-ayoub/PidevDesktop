package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class FoodDetailsController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label caloriesLabel;
    @FXML
    private Label proteinLabel;
    @FXML
    private Label carbsLabel;
    @FXML
    private Label fatLabel;
    @FXML
    private Label servingSizeLabel;
    @FXML
    private Label servingUnitLabel;

    private Food currentFood;
    private FoodService foodService = new FoodService();

    public void setFood(Food food) {
        this.currentFood = food;
        nameLabel.setText(food.getName());
        caloriesLabel.setText(String.valueOf(food.getCalories()));
        proteinLabel.setText(String.valueOf(food.getProtein()));
        carbsLabel.setText(String.valueOf(food.getCarbohydrates()));
        fatLabel.setText(String.valueOf(food.getFat()));
        servingSizeLabel.setText(String.valueOf(food.getServingSize()));
        servingUnitLabel.setText(food.getServingUnit());
    }

    public void addFood(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addFood.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    public void viewFood(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewFood.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    public void recipeNavigate(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewRecipe.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    @FXML
    void edit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editFoodDetails.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EditFoodController controller = fxmlLoader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            controller.setFood(currentFood);
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setContentText("Are you sure you want to delete " + currentFood.getName() + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                boolean isDeleted = foodService.supprimer(currentFood.getId());
                Alert alert;
                if (isDeleted) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("DONE");
                    alert.setContentText("Food Deleted Successfully");
                    alert.showAndWait();

                    // Load viewfood.fxml and set it as the current scene
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewFood.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO");
                    alert.setContentText("Food name not found in the database, no deletion was performed");
                    alert.showAndWait();
                }
            } catch (SQLException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR !!!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setContentText("Deletion cancelled by the user");
            alert.showAndWait();
        }
    }

    public void bminavigate(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bmi.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }
}