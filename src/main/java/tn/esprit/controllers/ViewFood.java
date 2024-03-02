package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewFood {
    @FXML
    public TextField searchTF;

    @FXML
    private ListView<Food> listF;

    @FXML
    void searchFoods(KeyEvent event) {
        String keyword = searchTF.getText();
        FoodService foodService = new FoodService();
        try {
            List<Food> foods = foodService.searchFoods(keyword);
            ObservableList<Food> observableList = FXCollections.observableList(foods);
            listF.setItems(observableList);
        } catch (SQLException e) {
            System.err.println("Failed to search foods: " + e.getMessage());
        }
    }

    @FXML
    void addFood(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addFood.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }
    }

    @FXML
    void viewFood(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewFood.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load the page: " + e.getMessage());
        }

    }

    @FXML
    void recipeNavigate(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewRecipe.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

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
            listF.setItems(observableList);
            listF.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Food item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%s, Calories: %d, Serving Size: %.2f %s",
                                item.getName(), item.getCalories(), item.getServingSize(), item.getServingUnit()));
                    }
                }
            });

            listF.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/foodDetails.fxml"));

                        Scene scene = new Scene(fxmlLoader.load());

                        FoodDetailsController controller = fxmlLoader.getController();

                        controller.setFood(newValue);

                        Stage stage = (Stage) listF.getScene().getWindow();

                        stage.setScene(scene);

                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Failed to load the page: " + e.getMessage());
                    }
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
