package tn.esprit.controllers;
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
import tn.esprit.services.exerciceService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AfficherEx {

    @FXML
    private TableColumn<exercice, String> descriptionEx;
    @FXML
    private TableColumn<exercice, String> muscleEX;
    @FXML
    private TableColumn<exercice, String> nomEx;
    @FXML
    private TableColumn<exercice, String> imageEx;

    @FXML
    private TableView<exercice> tableView;
    @FXML
    private TextField RechercherTF;
    @FXML
    private ComboBox<String> muscleCibleComboBox;


    @FXML
    private TableColumn<exercice, Void> actionCol;
    exerciceService es = new exerciceService();

    @FXML
    void initialize() {
        try {
            List<exercice> exercices = es.recuperer();
            ObservableList<exercice> observableList = FXCollections.observableArrayList(exercices);

            FilteredList<exercice> filteredData = new FilteredList<>(observableList, b -> true);
            RechercherTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(exercice -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (exercice.getNOM().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (exercice.getDESCRIPTION().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (exercice.getMUSCLE_CIBLE().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else return false;
                });
            });
            SortedList<exercice> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
            nomEx.setCellValueFactory(new PropertyValueFactory<>("NOM"));
            descriptionEx.setCellValueFactory((new PropertyValueFactory<>("DESCRIPTION")));
            muscleEX.setCellValueFactory(new PropertyValueFactory<>("MUSCLE_CIBLE"));
            imageEx.setCellValueFactory(new PropertyValueFactory<>("IMAGE_URL"));
            imageEx.setCellFactory(column -> new TableCell<exercice, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);

                    if (empty || imagePath == null || imagePath.isEmpty()) {
                        setGraphic(null);
                    } else {
                        try {
                            System.out.println("Image path: " + imagePath); // print the image path
                            URL url = new URL(imagePath);
                            Image image = new Image(url.toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            setGraphic(null);
                            e.printStackTrace(); // print the exception
                        }
                    }
                }
            });

            actionCol.setCellFactory(param -> new TableCell<exercice, Void>() {
                private final ImageView editIcon;
                private final ImageView deleteIcon;
                private final ImageView addIcon;
                private final Button editButton;
                private final Button deleteButton;
                private final Button addButton ;

                {
                    editIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/edit.png")));
                    editIcon.setFitWidth(30);
                    editIcon.setFitHeight(30);

                    deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/traaash.png")));
                    deleteIcon.setFitWidth(30);
                    deleteIcon.setFitHeight(30);

                    addIcon = new ImageView(new Image((getClass().getResourceAsStream("/img/add-button.png"))));
                    addIcon.setFitWidth(30); // Adjust size as needed
                    addIcon.setFitHeight(30); // Adjust size as needed

                    editButton = new Button("", editIcon);
                    deleteButton = new Button("", deleteIcon);
                    addButton = new Button("",addIcon);

                    editButton.setOnAction(event -> {
                        exercice currentEx = getTableView().getItems().get(getIndex());
                        handleEditAction(currentEx);
                    });

                    deleteButton.setOnAction(event -> {
                        exercice currentEx = getTableView().getItems().get(getIndex());
                        handleDeleteAction(currentEx);
                    });
                    addButton.setOnAction(event -> handleAddAction());
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox(addButton ,editButton, deleteButton);
                        container.setAlignment(Pos.CENTER);
                        container.setSpacing(10);
                        setGraphic(container);
                    }
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        try {
            List<String> muscleCibles = es.getUniqueMuscleCibles();
            muscleCibleComboBox.getItems().addAll(muscleCibles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleAddAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));
            Parent addRoot = loader.load();
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(addRoot);
            currentStage.setScene(scene);

            // Optional: If you want to set the title of the window
            currentStage.setTitle("Ajouter un nouvel exercice");

            // Show the updated stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Loading", "Cannot load the add exercise view.", e.getMessage());
        }
    }

    private void handleEditAction(exercice ex) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditExercice.fxml"));
            Parent editRoot = loader.load();

            EditExerciceController editController = loader.getController();
            editController.setExerciceData(ex);

            Scene scene = new Scene(editRoot);
            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setScene(scene);
            editStage.showAndWait();

            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur de chargement", "Impossible de charger la vue d'édition.", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue.", e.getMessage());
        }
    }

    private void refreshTableView() {
        try {
            List<exercice> exercices = es.recuperer();
            ObservableList<exercice> tableData = FXCollections.observableArrayList(exercices);
            tableView.setItems(tableData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de rafraîchissement", "Impossible de rafraîchir les données.", e.getMessage());
        }
    }


    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleDeleteAction(exercice ex) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Exercise");
        alert.setContentText("Are you sure you want to delete this exercise?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    es.deletePlanExerciceByExerciceId(ex.getID());
                    es.supprimer(ex.getID());
                    refreshTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Delete Error", "Failed to delete the exercise.", e.getMessage());
                }
            }
        });
    }

}