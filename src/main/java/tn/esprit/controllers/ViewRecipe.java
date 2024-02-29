package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private TextField searchTF;
    @FXML
    private ListView<Recipe> listR;

    @FXML
    void searchByKeyword(KeyEvent event) {
        String keyword = searchTF.getText();
        RecipeService recipeService = new RecipeService();
        try {
            List<Recipe> recipes = recipeService.searchByKeyword(keyword);
            for (Recipe recipe : recipes) {
                List<Food> foods = recipeService.getFoodsInRecipe(recipe.getIdRecipe());
                recipe.setFoods(foods);
            }
            ObservableList<Recipe> observableList = FXCollections.observableList(recipes);
            listR.setItems(observableList);
        } catch (SQLException e) {
            System.err.println("Failed to search foods: " + e.getMessage());
        }
    }

    @FXML
    void addRecipe(ActionEvent event) {
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

    @FXML
    void deleteRecipe(ActionEvent event) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/deleteRecipe.fxml"));

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
    void editRecipe(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editRecipe.fxml"));

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
    void foodNavigate(ActionEvent event) {
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
    void viewRecipe(ActionEvent event) {
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

    @FXML
    void initialize() {
        RecipeService recipeService = new RecipeService();
        try {
            List<Recipe> recipes = recipeService.recuperer();
            for (Recipe recipe : recipes) {
                List<Food> foods = recipeService.getFoodsInRecipe(recipe.getIdRecipe());
                recipe.setFoods(foods);
            }
            ObservableList<Recipe> observableList = FXCollections.observableList(recipes);
            listR.setItems(observableList);
            listR.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Recipe item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        ListView<Food> foodListView = new ListView<>();
                        foodListView.setPrefSize(200, 100); // Set preferred width and height
                        foodListView.setMaxHeight(100); // Set max height
                        ObservableList<Food> foodObservableList = FXCollections.observableList(item.getFoods());
                        foodListView.setItems(foodObservableList);
                        foodListView.setCellFactory(param -> new ListCell<>() {
                            @Override
                            protected void updateItem(Food item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getName() == null) {
                                    setText(null);
                                } else {
                                    Text text = new Text(item.getName());
                                    text.setWrappingWidth(180); // Set wrapping width
                                    setGraphic(text);
                                }
                            }
                        });

                        Label nameLabel = new Label("Name: " + item.getName());
                        Label caloriesLabel = new Label("Total Calories: " + item.getTotalCalories());
                        VBox vbox = new VBox(nameLabel, caloriesLabel, foodListView);
                        setGraphic(vbox);
                    }
                }
            });
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }    private void changeScene(ActionEvent event, String fxml) {
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
}
