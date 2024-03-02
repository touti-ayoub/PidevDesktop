package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFood implements Initializable {
    @FXML
    private TextField caloriesTF;

    @FXML
    private TextField carbsTF;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private TextField fatTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField proteinTF;

    @FXML
    private TextField servsizeTF;

    @FXML
    void Select(ActionEvent event) {

    }
    @FXML
    void inesrtFood(ActionEvent event) {
        FoodService foodService = new FoodService();
        Food food = new Food();
        food.setName(nameTF.getText());
        food.setCalories(Integer.parseInt(caloriesTF.getText()));
        food.setProtein(Integer.parseInt(proteinTF.getText()));
        food.setCarbohydrates(Integer.parseInt(carbsTF.getText()));
        food.setFat(Integer.parseInt(fatTF.getText()));
        food.setServingSize(Double.parseDouble(servsizeTF.getText()));
        food.setServingUnit(combo.getValue()); // get the selected item from the combo box

        // Add validation for nameTF
        if (food.getName().length() < 2) {
            showAlert("Name must be at least 2 letters");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setContentText("Are you sure you want to add this food item?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                foodService.ajouter(food);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("DONE");
                alert.setContentText("Food Added Successfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR !!!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(FXCollections.observableArrayList("gram","ounce","cup","litre"));

        // Add validation for numeric fields
        addNumericValidation(caloriesTF, "Calories must be a number");
        addNumericValidation(carbsTF, "Carbs must be a number");
        addNumericValidation(fatTF, "Fat must be a number");
        addNumericValidation(proteinTF, "Protein must be a number");
        addNumericValidation(servsizeTF, "Serving size must be a number");
    }

    private void addNumericValidation(TextField textField, String errorMessage) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
                textField.setStyle("-fx-border-color: red;");
                showAlert(errorMessage);
            } else {
                textField.setStyle("");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}
