package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Competition;
import tn.esprit.services.CompetitionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CalendrierCompetition implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // Liste des compétitions pour un mois donné
        Map<Integer, List<Competition>> calendarCompetitionsMap = getCalendarCompetitionsMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Vérifier pour une année bissextile
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<Competition> calendarCompetitions = calendarCompetitionsMap.get(currentDate);
                        if (calendarCompetitions != null) {
                            createCalendarCompetitions(calendarCompetitions, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarCompetitions(List<Competition> competitions, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarCompetitionsBox = new VBox();
        for (Competition competition : competitions) {
            Text text = new Text(competition.getLibelle());
            calendarCompetitionsBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                // Action when you click on a competition
                moveToAnotherInterfaceUser(competition);
            });
        }

        calendarCompetitionsBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarCompetitionsBox.setMaxWidth(rectangleWidth * 0.8);
        calendarCompetitionsBox.setMaxHeight(rectangleHeight * 0.65);
        calendarCompetitionsBox.setStyle("-fx-background-color:GRAY");

        stackPane.getChildren().add(calendarCompetitionsBox);
    }


    private Map<Integer, List<Competition>> createCalendarMap(List<Competition> calendarCompetitions) {
        Map<Integer, List<Competition>> calendarCompetitionsMap = new HashMap<>();

        for (Competition competition : calendarCompetitions) {
            int competitionDate = competition.getDateDebut().toLocalDate().getDayOfMonth();
            if (!calendarCompetitionsMap.containsKey(competitionDate)) {
                calendarCompetitionsMap.put(competitionDate, new ArrayList<>(List.of(competition)));
            } else {
                List<Competition> oldListByDate = calendarCompetitionsMap.get(competitionDate);

                List<Competition> newList = new ArrayList<>(oldListByDate);
                newList.add(competition);
                calendarCompetitionsMap.put(competitionDate, newList);
            }
        }
        return calendarCompetitionsMap;
    }

    private List<Competition> getCompetitionsMonth(ZonedDateTime dateFocus) {
        CompetitionService competitionService = new CompetitionService();
        try {
            return competitionService.getCompetitionsByMonth(dateFocus.getMonthValue(), dateFocus.getYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, List<Competition>> getCalendarCompetitionsMonth(ZonedDateTime dateFocus) {
        List<Competition> competitions = getCompetitionsMonth(dateFocus);
        return createCalendarMap(competitions);
    }

    private void moveToAnotherInterfaceUser(Competition competition) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherCompetitionUser.fxml"));
            Parent root = loader.load();

            // Obtain the controller of the new interface and pass the data
            afficherCompetitionUser controller = loader.getController();
            controller.initData(competition);

            // Create a new scene and set it in your main stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Show the new scene
              stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (display an error message, logging, etc.)
        }
    }
    @FXML
    private void handleListeDemandeButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listeParticipation.fxml"));
            Parent root = loader.load();

            // If CalendrierCompetition.fxml controller needs initialization, you can get the controller instance
            // CalendrierCompetitionController controllerCalendrierCompetition = loader.getController();
            // Perform any initialization if needed

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Optionally, you can close the current stage (listeParticipation)
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (display an error message, logging, etc.)
        }
    }
}
