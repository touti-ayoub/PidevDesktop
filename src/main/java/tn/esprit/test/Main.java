package tn.esprit.test;

import tn.esprit.models.coach;
import tn.esprit.services.coachService;
import tn.esprit.utils.MyDatabase;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
     coachService cs = new coachService();
        try {
           // cs.ajouter(new coach(21,6,100.0f,"zeineb","jmal","url","biographie","tennis"));
          //  cs.ajouter(new coach(27,8,180.0f,"ayoub","touti","url","biographie","musculation"));
          //  cs.ajouter(new coach(25,7,160.0f,"samar","saddouri","url","biographie","yoga"));
          //  cs.ajouter(new coach(20,6,100.0f,"oussema","jmal","url","biographie","natation"));

            // cs.supprimer(1);
            System.out.println(cs.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
