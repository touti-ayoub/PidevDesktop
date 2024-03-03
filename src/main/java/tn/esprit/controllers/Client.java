package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.planService;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.List;

public class Client {
    @FXML
    private GridPane plansExercisesGrid;
    private planService ps = new planService();

    @FXML
    public void initialize() {
        try {
            List<plan> plans = ps.recuperer(); // Suppose que cette méthode récupère tous les plans
            int row = 0; // Vous pouvez avoir besoin de gérer à la fois la ligne et la colonne si vous voulez un vrai GridView
            int column = 0;
            final int maxColumn = 3; // Nombre maximum de colonnes par exemple
            for (plan p : plans) {
                VBox planBox = new VBox(5); // L'espacement entre les éléments
                // Créez une ImageView pour l'image du plan
                ImageView planImageView = new ImageView();
                if (p.getIMAGE_URL() != null && !p.getIMAGE_URL().isEmpty()) {
                    planImageView.setImage(new Image(p.getIMAGE_URL()));
                    planImageView.setFitHeight(100); // Ajustez selon vos besoins
                    planImageView.setFitWidth(100); // Ajustez selon vos besoins
                    planBox.getChildren().add(planImageView);
                }

                Label nameLabel = new Label(p.getNOM());
                nameLabel.getStyleClass().add("plan-label"); // Appliquer la classe CSS au label du nom
                planBox.getChildren().add(nameLabel);

                // Si vous avez un label séparé pour la description, faites comme ceci :
                Label descriptionLabel = new Label(p.getDESCRIPTION());
                descriptionLabel.setWrapText(true);
                descriptionLabel.setMaxWidth(200);
                descriptionLabel.getStyleClass().add("description-label"); // Appliquer la classe CSS au label de la description
                planBox.getChildren().add(descriptionLabel);

                List<exercice> exercices = ps.getExercicesForPlan(p.getID()); // Méthode pour récupérer les exercices d'un plan
                for (exercice ex : exercices) {
                    HBox hbox = new HBox(5); // Espacement entre les éléments du HBox
                    Label exLabel = new Label(ex.getNOM() + " - " + ex.getMUSCLE_CIBLE());
                    ImageView exImageView = new ImageView();
                    if (ex.getIMAGE_URL() != null && !ex.getIMAGE_URL().isEmpty()) {
                        exImageView.setImage(new Image(ex.getIMAGE_URL()));
                        exImageView.setFitHeight(50); // Hauteur de l'image
                        exImageView.setFitWidth(50); // Largeur de l'image
                    }
                    hbox.getChildren().addAll(exImageView, exLabel);
                    planBox.getChildren().add(hbox);
                }

                plansExercisesGrid.add(planBox, column, row);
                GridPane.setMargin(planBox, new Insets(10)); // Applique une marge autour de chaque VBox

                // Gestion de la mise en page pour passer à la nouvelle ligne/colonne selon votre conception de grille
                column++;
                if (column >= maxColumn) { // Changez ce nombre en fonction du nombre de colonnes que vous souhaitez
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
