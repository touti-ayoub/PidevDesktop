package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.models.plan;
import tn.esprit.services.planService;

public class EditPlanController {
    @FXML
    private TextField nomPlan;
    @FXML
    private TextField descriptionPlan;



    private plan currentPlan;
    private final planService ps = new planService();

    public void setPlanData(plan p) {
        currentPlan = p;
        nomPlan.setText(p.getNOM());
        descriptionPlan.setText(p.getDESCRIPTION());


    }

    // Méthode pour sauvegarder les modifications
    @FXML
    private void savePlan() {
        currentPlan.setNOM(nomPlan.getText());
        currentPlan.setDESCRIPTION(descriptionPlan.getText());
        try {
            ps.modifier(currentPlan);
            // Fermez la fenêtre ou affichez un message de succès
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez l'erreur
        }
    }


}
