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
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditFoodController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField caloriesField;
    @FXML
    private TextField proteinField;
    @FXML
    private TextField carbsField;
    @FXML
    private TextField fatField;
    @FXML
    private TextField servingSizeField;
    @FXML
    private ComboBox<String> combo;

    private Food currentFood;
    private FoodService foodService = new FoodService();

    public void setFood(Food food) {
        this.currentFood = food;
        nameField.setText(food.getName());
        caloriesField.setText(String.valueOf(food.getCalories()));
        proteinField.setText(String.valueOf(food.getProtein()));
        carbsField.setText(String.valueOf(food.getCarbohydrates()));
        fatField.setText(String.valueOf(food.getFat()));
        servingSizeField.setText(String.valueOf(food.getServingSize()));
        combo.setValue(food.getServingUnit());
    }

    public void submitForm(ActionEvent actionEvent) {
        // get the new details from the form fields
        String newName = nameField.getText();
        if (newName.length() < 2) {
            showAlert("Name must have at least 2 letters");
            return;
        }
        int newCalories = Integer.parseInt(caloriesField.getText());
        int newProtein = Integer.parseInt(proteinField.getText());
        int newCarbs = Integer.parseInt(carbsField.getText());
        int newFat = Integer.parseInt(fatField.getText());
        double newServingSize = Double.parseDouble(servingSizeField.getText());
        String newServingUnit = combo.getValue();

        // update the current food details
        currentFood.setName(newName);
        currentFood.setCalories(newCalories);
        currentFood.setProtein(newProtein);
        currentFood.setCarbohydrates(newCarbs);
        currentFood.setFat(newFat);
        currentFood.setServingSize(newServingSize);
        currentFood.setServingUnit(newServingUnit);

        try {
            // update the food details in the database
            foodService.modifier(currentFood);
        } catch (SQLException e) {
            System.err.println("Failed to update the food: " + e.getMessage());
        }
    }

    public void addFood(ActionEvent actionEvent) {
    }

    public void viewFood(ActionEvent actionEvent) {
    }

    public void recipeNavigate(ActionEvent actionEvent) {
    }

    public void Select(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(FXCollections.observableArrayList("gram","ounce","cup","litre"));
        // Add validation for numeric fields
        addNumericValidation(caloriesField, "Calories must be a number");
        addNumericValidation(carbsField, "Carbs must be a number");
        addNumericValidation(fatField, "Fat must be a number");
        addNumericValidation(proteinField, "Protein must be a number");
        addNumericValidation(servingSizeField, "Serving size must be a number");
    }

    private void addNumericValidation(TextField textField, String errorMessage) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d+)?")) {
                textField.setText(newValue.replaceAll("[^\\d.]", "").replaceAll("(\\..*)\\.", "$1"));
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