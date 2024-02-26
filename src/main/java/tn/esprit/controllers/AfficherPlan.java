package tn.esprit.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.plan;
import tn.esprit.services.planService;
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



        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur
        }
    }

    private void showPlanDetails(plan plan) {
        // Ouvrir une nouvelle vue ou un dialogue pour afficher les détails du plan sélectionné
        // Cette méthode dépend de la manière dont vous souhaitez implémenter la vue des détails
    }
}
