package tn.esprit.test;

import tn.esprit.services.plan_exercice;
import tn.esprit.models.coach;
import tn.esprit.models.plan;
import tn.esprit.models.exercice;
import tn.esprit.services.exerciceService;
import tn.esprit.services.planService;
import tn.esprit.utils.MyDatabase;
import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        planService cs = new planService();
        // plan_exercice cs = new plan_exercice();
        exerciceService c = new exerciceService();
        try {
            // cs.ajouter(new plan("debutant","pour les nouveaux"));
            //  cs.ajouter(new exercice(1 ,"squat","20 fois"));
            // cs.modifier(new plan(1,"avancee","pour les anciens"));
            //cs.supprimer(1);

            //List<exercice> c1 = c.recuperer();
            // System.out.println(c1);
            // cs.ajouterPlan(new plan(1,"debutant","pour les nouveaux"),c1);
            //  System.out.println(cs.recuperer());
            // plan_exercice plan_exercice = new plan_exercice();
            //plan_exercice.afficherAssociations();
            // cs.afficherAssociations();
               plan p = cs.rechercherParNom("debutant");
            System.out.println(p);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
