package tn.esprit.test;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.services.exerciceService;
import tn.esprit.services.planService;
import tn.esprit.services.plan_exercice;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        planService ps = new planService();
        plan_exercice pe = new plan_exercice();
        exerciceService es = new exerciceService();
        try {
            // ps.ajouter(new plan("debutant","pour les nouveaux"));
            //es.ajouter(new exercice( "Leg extensionmmmm", "3 Sets 12-10-8 Reps ","legs","nn"));
            // cs.modifier(new plan(1,"avancee","pour les anciens"));
            //cs.supprimer(1);

              List<exercice> c1 = es.recuperer();
               System.out.println(c1);
           //    List<exercice> exercices = new ArrayList<>();
            // Ajoutez les exercices que vous voulez associer
            //    exercice exercice1 = new exercice();
            //    exercice1.setID(2); // Supposons que vous avez un identifiant pour chaque exercice
            //    exercices.add(exercice1);
            //    exercice exercice2 = new exercice();
            //     exercice2.setID(2);
            //     exercices.add(exercice2);
            // Récupérer les exercices depuis la base de données
           // List<exercice> list1 = new ArrayList<>();
           // list1.add(es.recupererExerciceParId(1)); // Exemple de récupération par ID
           // list1.add(es.recupererExerciceParId(2));
           // System.out.println(list1);
         //   ps.ajouterPlan(new plan("Sech 1", "6 Week"), list1);
            //  System.out.println(cs.recuperer());
            // plan_exercice plan_exercice = new plan_exercice();
            //plan_exercice.afficherAssociations();
            // cs.afficherAssociations();
            //   plan p = cs.rechercherParNom("debutant");
            // System.out.println(p);
          //  Arrays.asList(
                //    new exercice(1, "Exercice 1"),
                 //   new exercice(2, "Exercice 2"),
                 //   new exercice(3, "Exercice 3")
          //  exercice e1 = es.recupererExerciceParId(1);
          //  System.out.println(e1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
