package tn.esprit.controllers;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;
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

    private final exerciceService es = new exerciceService();

    @FXML
    void initialize() {

        try {
            List<exercice> exercices = es.recuperer();
            ObservableList<exercice> observableList = FXCollections.observableList(exercices);
            tableView.setItems(observableList);
            nomEx.setCellValueFactory(new PropertyValueFactory<>("NOM"));
            descriptionEx.setCellValueFactory((new PropertyValueFactory<>("DESCRIPTION")));
            muscleEX.setCellValueFactory(new PropertyValueFactory<>("MUSCLE_CIBLE"));
            imageEx.setCellValueFactory(new PropertyValueFactory<>("IMAGE_URL"));

            // Ajout de la gestion de l'affichage de l'image
            imageEx.setCellFactory(column -> new TableCell<exercice, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);

                    if (empty || imagePath == null) {
                        setGraphic(null);
                    } else {
                        try {
                            // Charger et afficher l'image associée à l'exercice
                            Image image = new Image(imagePath);
                            imageView.setImage(image);
                            imageView.setFitWidth(50); // Ajuster la largeur de l'image selon vos besoins
                            imageView.setFitHeight(50); // Ajuster la hauteur de l'image selon vos besoins
                            setGraphic(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Gérer l'erreur de chargement de l'image
                        }
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

    }
}

