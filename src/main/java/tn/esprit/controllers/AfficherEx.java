package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import tn.esprit.controllers.EditExerciceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.exerciceService;
import tn.esprit.services.planService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AfficherEx {

    @FXML
    exerciceService es = new exerciceService();


        @FXML
        private ListView<exercice> listE;
        @FXML
        private TextField RechercherTF;

        @FXML
        void searchExercice(KeyEvent event) {
            String keyword = RechercherTF.getText();
            System.out.println("Searching for: " + keyword); // Print the keyword
            exerciceService es = new exerciceService();
            try {
                List<exercice> exercices = es.rechercherParNom(keyword);
                System.out.println("Search results: " + exercices); // Print the search results
                ObservableList<exercice> observableList = FXCollections.observableList(exercices);
                listE.setItems(observableList);
            } catch (SQLException e) {
                System.err.println("Failed to search foods: " + e.getMessage());
            }
        }

        @FXML
        void initialize() {
            exerciceService es = new exerciceService();
            try {
                List<exercice> exercices = es.recuperer();
                ObservableList<exercice> observableList = FXCollections.observableList(exercices);
                listE.setItems(observableList);
                listE.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(exercice item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(String.format("Nom: %s, Description: %s, Muscle cible: %s",
                                    item.getNOM(), item.getDESCRIPTION(), item.getMUSCLE_CIBLE()));
                        }
                    }
                });

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    @FXML
    private void handleExerciceClick() throws SQLException {
        exercice selectedExercice = listE.getSelectionModel().getSelectedItem();
        if (selectedExercice != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ExerciceDetail.fxml"));
                Parent root = loader.load();

                ExerciceDetail controller = loader.getController();
                controller.setExercice(selectedExercice);

                // Get the current stage and set the new scene on it
                Stage stage = (Stage) listE.getScene().getWindow();
                stage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        @FXML
        void AfficherExercice(ActionEvent event) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherEx.fxml"));

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
        public void AjouterExercice(ActionEvent actionEvent) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));

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
    }



