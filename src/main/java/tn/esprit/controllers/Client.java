package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.planService;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    @FXML
    private GridPane plansExercisesGrid;
    private planService ps = new planService();

    // Placeholder for user's premium access status
    // In a real application, this would be determined by the user's account status
    private boolean hasPaidForPremium = false;

    @FXML
    public void initialize() {
        try {
            List<plan> plans = ps.recuperer();
            // Sort plans by likes in descending order
            plans.sort(Comparator.comparing(plan::getLIKES).reversed());

            // Split into premium and non-premium plans
            List<plan> premiumPlans = plans.stream().limit(3).collect(Collectors.toList());
            List<plan> nonPremiumPlans = plans.stream().skip(3).collect(Collectors.toList());

            // Display non-premium plans by default
            displayPlans(nonPremiumPlans, 0, 0, false);

            // If user has not paid for premium, show payment button
                // If user has paid, display premium plans as well
                displayPlans(premiumPlans, plansExercisesGrid.getRowCount(), 0, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    private void displayPaymentButton() {
        Button payForPremiumButton = new Button("Access Premium Plans");
        payForPremiumButton.setOnAction(event -> {
            // Assuming processPayment() is securely triggered on the server
            try {
                // Directly calling for demonstration; in real scenarios, this should happen on the server
                new payementController().processPayment();
                // Simulate checking payment success
                hasPaidForPremium = true; // Update this based on actual payment success
                plansExercisesGrid.getChildren().clear(); // Clear existing UI components
                initialize(); // Refresh the UI to display premium plans
            } catch (Exception e) {
                System.out.println("Payment processing error: " + e.getMessage());
                // Handle failed payment attempt, e.g., show an error message to the user
            }
        });

        // Positioning the payment button dynamically can be complex; as an example, add it to the bottom of the grid
        plansExercisesGrid.add(payForPremiumButton, 0, plansExercisesGrid.getRowCount(), 3, 1);
        GridPane.setMargin(payForPremiumButton, new Insets(20, 0, 20, 0));
    }*/

    private Button createPaymentButton(plan p) {
        Button payForPremiumButton = new Button("Buy This Premium Plan");
        payForPremiumButton.setOnAction(event -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentForm.fxml"));
                Parent root = loader.load();

                // Create a new scene and stage
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                // Show the payment form
                stage.show();

            } catch (IOException e) {
                System.out.println("Payment processing error: " + e.getMessage());
                // Handle failed payment attempt, e.g., show an error message to the user
            }
        });
        return payForPremiumButton;
    }

    private void displayPlans(List<plan> plans, int startRow, int startColumn, boolean isPremium) {
        int row = startRow;
        int column = startColumn;
        final int maxColumn = 3;

        for (plan p : plans) {
            VBox planBox = createPlanBox(p, isPremium);
            if (isPremium) {
                Button payForPremiumButton = createPaymentButton(p);
                planBox.getChildren().add(payForPremiumButton);
            }
            plansExercisesGrid.add(planBox, column, row);
            GridPane.setMargin(planBox, new Insets(10));

            column++;
            if (column >= maxColumn) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createPlanBox(plan p, boolean isPremium) {
        VBox planBox = new VBox(5);
        ImageView planImageView = new ImageView();
        if (p.getIMAGE_URL() != null && !p.getIMAGE_URL().isEmpty()) {
            planImageView.setImage(new Image(p.getIMAGE_URL()));
            planImageView.setFitHeight(100);
            planImageView.setFitWidth(100);
            planBox.getChildren().add(planImageView);
        }

        Label nameLabel = new Label(p.getNOM());
        nameLabel.getStyleClass().add("plan-label");
        planBox.getChildren().add(nameLabel);

        Label descriptionLabel = new Label(p.getDESCRIPTION());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(200);
        descriptionLabel.getStyleClass().add("description-label");
        planBox.getChildren().add(descriptionLabel);

        if (!isPremium) {
            try {
                List<exercice> exercices = ps.getExercicesForPlan(p.getID());
                for (exercice ex : exercices) {
                    HBox hbox = new HBox(5);
                    Label exLabel = new Label(ex.getNOM() + " - " + ex.getMUSCLE_CIBLE());
                    ImageView exImageView = new ImageView();
                    if (ex.getIMAGE_URL() != null && !ex.getIMAGE_URL().isEmpty()) {
                        exImageView.setImage(new Image(ex.getIMAGE_URL()));
                        exImageView.setFitHeight(50);
                        exImageView.setFitWidth(50);
                    }
                    hbox.getChildren().addAll(exImageView, exLabel);
                    planBox.getChildren().add(hbox);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Label likesLabel = new Label("Likes: " + p.getLIKES());
        planBox.getChildren().add(likesLabel);

        ImageView likeIcon = new ImageView(new Image("img/like.png"));
        likeIcon.setFitHeight(30);
        likeIcon.setFitWidth(30);
        likeIcon.setOnMouseClicked(event -> {
            try {
                ps.incrementerLikes(p.getID());
                int updatedLikes = p.getLIKES() + 1;
                likesLabel.setText("Likes: " + updatedLikes);
                p.setLIKES(updatedLikes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        planBox.getChildren().add(likeIcon);
        return planBox;
    }
}