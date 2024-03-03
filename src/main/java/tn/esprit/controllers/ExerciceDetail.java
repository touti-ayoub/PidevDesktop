package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.services.exerciceService;
import javafx.scene.image.Image;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import java.sql.SQLException;
import java.util.List;


public class ExerciceDetail {


    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView imageView;

    private exerciceService es = new exerciceService();
    private exercice currentExercice;

    public void setExercice(exercice e) throws SQLException {
        this.currentExercice = e;
        nameLabel.setText(e.getNOM());
        descriptionLabel.setText(e.getDESCRIPTION());
        imageView.setImage(new Image(e.getIMAGE_URL()));
    }
    @FXML
    private void handleDeleteButton() throws SQLException {
        if (currentExercice != null) {
            es.supprimer(currentExercice.getID());
            // Refresh the view or close the window
        }
    }
}
