package tn.esprit.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.planService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AfficherPlan {



    @FXML
    private ListView<plan> listP;
    @FXML
    private TextField RechercherTF;

    @FXML
    void searchPlans(KeyEvent event) {
        String keyword = RechercherTF.getText();
        System.out.println("Searching for: " + keyword); // Print the keyword
        planService ps = new planService();
        try {
            List<plan> plans = ps.rechercherParNom(keyword);
            System.out.println("Search results: " + plans); // Print the search results
            ObservableList<plan> observableList = FXCollections.observableList(plans);
            listP.setItems(observableList);
        } catch (SQLException e) {
            System.err.println("Failed to search foods: " + e.getMessage());
        }
    }
    @FXML
    private void handlePlanClick() throws SQLException {
        plan selectedPlan = listP.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlanDetail.fxml"));
                Parent root = loader.load();

                PlanDetail controller = loader.getController();
                controller.setPlan(selectedPlan);

                // Get the current stage and set the new scene on it
                Stage stage = (Stage) listP.getScene().getWindow();
                stage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        planService ps = new planService();
        try {
            List<plan> plans = ps.recuperer();
            ObservableList<plan> observableList = FXCollections.observableList(plans);
            listP.setItems(observableList);
            listP.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(plan item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        VBox vbox = new VBox();
                        Label planLabel = new Label(String.format("Nom: %s, Likes: %d, Description: %s",
                                item.getNOM(), item.getLIKES(), item.getDESCRIPTION()));
                        vbox.getChildren().add(planLabel);

                        ListView<exercice> listView = new ListView<>();
                        listView.setMaxHeight(100); // Limit the height of the ListView

                        try {
                            plan planWithExercises = ps.recupererPlanAvecExercices(item.getID());
                            ObservableList<exercice> exercices = FXCollections.observableArrayList(planWithExercises.getExercices());
                            listView.setItems(exercices);
                            listView.setCellFactory(param -> new ListCell<>() {
                                @Override
                                protected void updateItem(exercice item, boolean empty) {
                                    super.updateItem(item, empty);

                                    if (empty || item == null) {
                                        setText(null);
                                    } else {
                                        setText(String.format("Name: %s, Description: %s,Muscle %s", item.getNOM(), item.getDESCRIPTION(),item.getMUSCLE_CIBLE()));
                                    }
                                }
                            });
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        vbox.getChildren().add(listView);
                        setGraphic(vbox);
                    }
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void AfficherPlan(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherPlan.fxml"));

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
    public void AjouterPlan(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterPlan.fxml"));

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
    public void AfficherExercice(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherEx.fxml"));

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