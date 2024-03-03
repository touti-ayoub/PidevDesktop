package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.models.Recipe;

import java.io.IOException;

public class RecipeDetails{
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
        private ListView<Food> foodListView;
        private Recipe currentRecipe;


    public void setRecipe(Recipe recipe) {
            this.currentRecipe = recipe;
            nameLabel.setText(recipe.getName());
            caloriesLabel.setText("Total Calories: " + recipe.getTotalCalories());
            proteinLabel.setText("Total Protein: " + recipe.getTotalProtein());
            carbsLabel.setText("Total Carbs: " + recipe.getTotalCarbs());
            fatLabel.setText("Total Fat: " + recipe.getTotalFat());
            ObservableList<Food> foodObservableList = FXCollections.observableList(recipe.getFoods());
            foodListView.setItems(foodObservableList);
        }

        public void addRecipe(ActionEvent actionEvent) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRecipe.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);

                stage.show();
            } catch (IOException e) {
                System.err.println("Failed to load the page: " + e.getMessage());
            }
        }

        public void viewRecipe(ActionEvent actionEvent) {
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

        public void foodNavigate(ActionEvent actionEvent) {
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

    public void edit(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editRecipe.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EditRecipe controller = fxmlLoader.getController();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            controller.setRecipe(currentRecipe);
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    public void delete(ActionEvent actionEvent) {
    }
}

