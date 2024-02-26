package tn.esprit.controllers;
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
import tn.esprit.services.planService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AfficherPlan {

    @FXML
    private TableColumn<plan, String> nomPlan;
    @FXML
    private TableColumn<plan, String> descriptionPlan;
    @FXML
    private TableColumn<plan, String> imagePlan;

    @FXML
    private TableView<plan> tableView;
    @FXML
    private TextField RechercherTF;
    @FXML
    private TableColumn<plan, Void> actionCol;

    private planService ps = new planService();


    @FXML
    public void initialize() {
        try {
            List<plan> plans = ps.recuperer(); // Récupérer les plans de la base de données
            ObservableList<plan> observableList = FXCollections.observableArrayList(plans);

            // Filtre pour la recherche
            FilteredList<plan> filteredData = new FilteredList<>(observableList, b -> true);
            RechercherTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(plan -> {
                    if (newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (plan.getNOM().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filtre sur le nom
                    } else if (plan.getDESCRIPTION().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filtre sur la description
                    }
                    return false; // Pas de correspondance
                });
            });

            SortedList<plan> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    showPlanDetails(newSelection);
                }
            });

            tableView.setItems(sortedData); // Assurez-vous que cette ligne est après la configuration de votre TableView

            tableView.setItems(sortedData);

            // Configuration des colonnes
            nomPlan.setCellValueFactory(new PropertyValueFactory<>("NOM"));
            descriptionPlan.setCellValueFactory(new PropertyValueFactory<>("DESCRIPTION"));
            imagePlan.setCellValueFactory(new PropertyValueFactory<>("IMAGE_URL"));
            imagePlan.setCellFactory(column -> new TableCell<plan, String>() {
                private final ImageView imageView = new ImageView();

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
                            imageView.setFitWidth(70);
                            imageView.setFitHeight(70);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            setGraphic(null);
                            e.printStackTrace(); // print the exception
                        }
                    }
                }

            });

            actionCol.setCellFactory(param -> new TableCell<plan, Void>() {
                private final ImageView editIcon;
                private final ImageView deleteIcon;
                private final ImageView addIcon;
                private final Button editButton;
                private final Button deleteButton;
                private final Button addButton ;

                {
                    editIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/edit.png")));
                    editIcon.setFitWidth(40);
                    editIcon.setFitHeight(40);

                    deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/traaash.png")));
                    deleteIcon.setFitWidth(40);
                    deleteIcon.setFitHeight(40);

                    addIcon = new ImageView(new Image((getClass().getResourceAsStream("/img/add-button.png"))));
                    addIcon.setFitWidth(40); // Adjust size as needed
                    addIcon.setFitHeight(40); // Adjust size as needed

                    editButton = new Button("", editIcon);
                    deleteButton = new Button("", deleteIcon);
                    addButton = new Button("",addIcon);

                    editButton.setOnAction(event -> {
                        plan currentP = getTableView().getItems().get(getIndex());
                        handleEditAction(currentP);
                    });

                    deleteButton.setOnAction(event -> {
                        plan currentP = getTableView().getItems().get(getIndex());                        handleDeleteAction(currentP);
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
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur
        }
    }

    private void handleAddAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPlan.fxml"));
            Parent addRoot = loader.load();
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(addRoot);
            currentStage.setScene(scene);

            // Optional: If you want to set the title of the window
            currentStage.setTitle("Ajouter un nouveau plan");

            // Show the updated stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Loading", "Cannot load the add exercise view.", e.getMessage());
        }
    }



    private void showPlanDetails(plan selectedPlan) {
        try {
            plan detailedPlan = ps.recupererPlanAvecExercices(selectedPlan.getID()); // Assurez-vous que cette méthode est bien implémentée dans votre planService

            StringBuilder details = new StringBuilder();
            details.append("Nom: ").append(detailedPlan.getNOM()).append("\n");
            details.append("Description: ").append(detailedPlan.getDESCRIPTION()).append("\n\n");
            details.append("Exercices:\n");
            for (exercice ex : detailedPlan.getExercices()) {
                details.append("- ").append(ex.getNOM()).append(": ").append(ex.getDESCRIPTION()).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails du Plan");
            alert.setHeaderText("Informations du Plan et Exercices Associés");
            alert.setContentText(details.toString());
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des erreurs
        }
    }
    private void handleEditAction(plan p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditPlan.fxml"));
            Parent editRoot = loader.load();

            EditPlanController editController = loader.getController();
            editController.setPlanData(p);

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
            List<plan> plans = ps.recuperer();
            ObservableList<plan> tableData = FXCollections.observableArrayList(plans);
            tableView.setItems(tableData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de rafraîchissement", "Impossible de rafraîchir les données.", e.getMessage());
        }
    }
    private void handleDeleteAction(plan p) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Plan");
        alert.setContentText("Are you sure you want to delete this exercise?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    ps.deletePlanExerciceByPlanId(p.getID());
                    ps.supprimer(p.getID());
                    refreshTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Delete Error", "Failed to delete the exercise.", e.getMessage());
                }
            }
        });
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
