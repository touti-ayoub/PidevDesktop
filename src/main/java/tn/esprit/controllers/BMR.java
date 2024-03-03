package tn.esprit.controllers;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Food;
import tn.esprit.services.FoodService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BMR {

    @FXML
    private Label bmrLabel;

    @FXML
    private Label caloriesLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField ageField;

    @FXML
    private ComboBox<String> sexComboBox;

    @FXML
    private ComboBox<String> activityLevelComboBox;

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

    public BMR() {
        foodService = new FoodService();
    }

    double bmr1;

    @FXML
    public void initialize() {
        sexComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        activityLevelComboBox.setItems(FXCollections.observableArrayList("Sedentary", "Lightly active", "Moderately active", "Very active", "Extremely active"));
        foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        carbohydratesColumn.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        servingSizeColumn.setCellValueFactory(new PropertyValueFactory<>("servingSize"));
        servingUnitColumn.setCellValueFactory(new PropertyValueFactory<>("servingUnit"));
        loadFoodSuggestions();
    }

    public double calculateBMR() {
        double height = 0;
        double weight = 0;
        int age = 0;

        if (!heightField.getText().isEmpty()) {
            height = Double.parseDouble(heightField.getText());
        }

        if (!weightField.getText().isEmpty()) {
            weight = Double.parseDouble(weightField.getText());
        }

        if (!ageField.getText().isEmpty()) {
            age = Integer.parseInt(ageField.getText());
        }

        String sex = sexComboBox.getValue();

        double bmr = 0;
        if (sex != null) {
            if (sex.equals("Male")) {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            } else if (sex.equals("Female")) {
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            }
        }
        bmr1 =bmr;

        String activityLevel = activityLevelComboBox.getValue();
        if (activityLevel != null) {
            switch (activityLevel) {
                case "Sedentary":
                    bmr *= 1.2;
                    break;
                case "Lightly active":
                    bmr *= 1.375;
                    break;
                case "Moderately active":
                    bmr *= 1.55;
                    break;
                case "Very active":
                    bmr *= 1.725;
                    break;
                case "Extremely active":
                    bmr *= 1.9;
                    break;
            }
        }
        return bmr;
    }

    private void loadFoodSuggestions() {
        double bmr = calculateBMR();
        try {
            List<Food> foods = foodService.getFoodSuggestions(bmr);
            foodTable.setItems(FXCollections.observableArrayList(foods));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayFoodSuggestions() {
        double bmr = calculateBMR();
        try {
            List<Food> foods = foodService.getFoodSuggestions(bmr);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bmr.fxml"));

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

    public void sendFoodSuggestionsAsSMS() {
        StringBuilder foodSuggestions = new StringBuilder("Food List:\n");
        for (Food food : foodTable.getItems()) {
            foodSuggestions.append(food.getName()).append("\n");
        }

        VonageClient client = VonageClient.builder().apiKey("6eb80c51").apiSecret("j5Kxzn4llZ3K1QNY").build();
        TextMessage message = new TextMessage("IronCoreGYM", phoneField.getText(), foodSuggestions.toString());

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }

    public void calculateAndDisplayBMR() {
        double bmr = calculateBMR();
        bmrLabel.setText("BMR: " + bmr1 + " calories/day");
        caloriesLabel.setText("TDEE: " + bmr+ " calories/day");
        loadFoodSuggestions();
        displayFoodSuggestions();
        sendFoodSuggestionsAsSMS();
    }
}