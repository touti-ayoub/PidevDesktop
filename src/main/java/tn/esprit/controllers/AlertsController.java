package tn.esprit.controllers;
import javafx.scene.control.Alert;
public class AlertsController {
    private static final AlertsController instance = new AlertsController();

    public static AlertsController get(){
        return instance;
    }

    public void Alert(String type, String title, String content){

        Alert alert;

        if(type.equals("information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        else{
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
