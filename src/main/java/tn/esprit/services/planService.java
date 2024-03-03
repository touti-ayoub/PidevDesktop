package tn.esprit.services;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class planService implements IService<plan> {
        private Connection connection;
        public planService(){
            connection = MyDatabase.getInstance().getConnection();
        }
     //   @Override
   //     public void ajouter(plan plan) throws SQLException {
     //      String req = "INSERT INTO plan (NOM,DESCRIPTION) VALUES('"+plan.getNOM()+"','"+plan.getDESCRIPTION()+"')";
       //     Statement st = connection.createStatement();
         //   st.executeUpdate(re

    @Override
    public void ajouter(plan plan) throws SQLException {

    }
    public List<exercice> getExercicesForPlan(int idPlan) throws SQLException {
        List<exercice> exercicesList = new ArrayList<>();

        String reqExercices = "SELECT e.* FROM exercice e JOIN plan_exercice pe ON e.IdExercice = pe.idExercice WHERE pe.idPlan = ?";
        PreparedStatement psExercices = connection.prepareStatement(reqExercices);
        psExercices.setInt(1, idPlan);
        ResultSet rsExercices = psExercices.executeQuery();
        while (rsExercices.next()) {
            exercice exercice = new exercice();
            exercice.setID(rsExercices.getInt("IdExercice"));
            exercice.setNOM(rsExercices.getString("NOM"));
            exercice.setDESCRIPTION(rsExercices.getString("DESCRIPTION"));
            exercice.setMUSCLE_CIBLE(rsExercices.getString("MUSCLE_CIBLE"));
            exercice.setIMAGE_URL(rsExercices.getString("IMAGE_URL"));
            exercicesList.add(exercice);
        }

        return exercicesList;
    }

    @Override
    public void ajouterPlan(plan plan, List<exercice> exercices) throws SQLException {
        try {
            // Ajouter le plan à la table Plan
                String reqPlan = "INSERT INTO plan (NOM, DESCRIPTION,IMAGE_URL) VALUES (?, ? ,?)";
              PreparedStatement stPlan = connection.prepareStatement(reqPlan, Statement.RETURN_GENERATED_KEYS);
             stPlan.setString(1, plan.getNOM());
              stPlan.setString(2, plan.getDESCRIPTION());
              stPlan.setString(3,plan.getIMAGE_URL());
              stPlan.executeUpdate();
            // Récupérer l'ID du plan nouvellement ajouté
               ResultSet generatedKeys = stPlan.getGeneratedKeys();
               int idPlan = -1;
              if (generatedKeys.next()) {
                idPlan = generatedKeys.getInt(1);
                 }

            // Ajouter les associations dans la table de liaison Plan_Exercice
                  if (idPlan != -1) {
                  String reqAssociation = "INSERT INTO plan_exercice (idPlan, idExercice) VALUES (?, ?)";
                   PreparedStatement stAssociation = connection.prepareStatement(reqAssociation);
                 for (exercice exercice : exercices) {
                     stAssociation.setInt(1, idPlan);
                     stAssociation.setInt(2, exercice.getID()); // Supposons que chaque exercice a un ID
                   stAssociation.executeUpdate();
             }
            }
             } catch (SQLException e) {
              e.printStackTrace();
        }
    }

    @Override
    public void modifier(plan plan) throws SQLException {
        String req = "UPDATE plan SET NOM=?, DESCRIPTION=?, IMAGE_URL=? WHERE IdPlan=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,plan.getNOM());
        ps.setString(2,plan.getDESCRIPTION());
        ps.setString(3,plan.getIMAGE_URL());
        ps.setInt(4,plan.getID());

        ps.executeUpdate();
    }


        @Override
        public void supprimer(int id) throws SQLException {
            String req = "DELETE FROM plan WHERE IdPlan = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1,id);
            ps.executeUpdate();
        }

        @Override
        public List<plan> recuperer() throws SQLException {
            List<plan> plans = new ArrayList<>();
            String req = "SELECT * FROM plan ";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                plan plan = new plan();
                plan.setID(rs.getInt("IdPlan"));
                plan.setNOM(rs.getString("NOM"));
                plan.setDESCRIPTION((rs.getString("DESCRIPTION")));
                plan.setIMAGE_URL(rs.getString("IMAGE_URL"));
                plan.setLIKES(rs.getInt("LIKES"));
                plans.add(plan);
            }
            System.out.println("Data from database: " + plans); // Print the data
            return plans;
        }
    public List<plan> rechercherParNom(String nom) throws SQLException {
        List<plan> plans = new ArrayList<>();
        String query = "SELECT * FROM plan WHERE NOM LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + nom + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plan plan = new plan();
                plan.setID(resultSet.getInt("IdPlan"));
                plan.setNOM(resultSet.getString("NOM"));
                plan.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
                plan.setLIKES(resultSet.getInt("LIKES"));
                // If other columns are present, you can add them in the same way
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans; // Return the list of matching plans
    }
    public plan recupererPlanAvecExercices(int idPlan) throws SQLException {
        plan Plan = null;
        List<exercice> exercicesList = new ArrayList<>();

        String reqPlan = "SELECT * FROM plan WHERE IdPlan = ?";
        PreparedStatement psPlan = connection.prepareStatement(reqPlan);
        psPlan.setInt(1, idPlan);
        ResultSet rsPlan = psPlan.executeQuery();
        if (rsPlan.next()) {
            Plan = new plan();
            Plan.setID(rsPlan.getInt("IdPlan"));
            Plan.setNOM(rsPlan.getString("NOM"));
            Plan.setDESCRIPTION(rsPlan.getString("DESCRIPTION"));

            String reqExercices = "SELECT e.* FROM exercice e JOIN plan_exercice pe ON e.IdExercice = pe.idExercice WHERE pe.idPlan = ?";
            PreparedStatement psExercices = connection.prepareStatement(reqExercices);
            psExercices.setInt(1, idPlan);
            ResultSet rsExercices = psExercices.executeQuery();
            while (rsExercices.next()) {
                exercice exercice = new exercice();
                exercice.setID(rsExercices.getInt("IdExercice"));
                exercice.setNOM(rsExercices.getString("NOM"));
                exercice.setDESCRIPTION(rsExercices.getString("DESCRIPTION"));
                exercice.setMUSCLE_CIBLE(rsExercices.getString("MUSCLE_CIBLE"));
                exercicesList.add(exercice);
            }
            Plan.setExercices(exercicesList); // Assurez-vous que votre classe plan a une méthode pour définir la liste des exercices
        }
        return Plan;
    }
    public void deletePlanExerciceByPlanId(int id) throws SQLException {
        String req = "DELETE FROM plan_exercice WHERE IdPlan = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public void incrementerLikes(int idPlan) throws SQLException {
        String sql = "UPDATE plan SET LIKES = LIKES + 1 WHERE IdPlan = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPlan);
            ps.executeUpdate();
        }
    }

    public List<plan> recupererTop5PlansLikes() throws SQLException {
        List<plan> topPlans = new ArrayList<>();
        String sql = "SELECT * FROM plan ORDER BY LIKES DESC LIMIT 5";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                plan p = new plan();
                p.setID(rs.getInt("IdPlan"));
                p.setNOM(rs.getString("NOM"));
                p.setDESCRIPTION(rs.getString("DESCRIPTION"));
                p.setIMAGE_URL(rs.getString("IMAGE_URL"));
                p.setLIKES(rs.getInt("LIKES"));
                topPlans.add(p);
            }
        }
        return topPlans;
    }
    public List<String> getUniqueNom() throws SQLException {
        List<String> nom = new ArrayList<>();
        String req = "SELECT DISTINCT NOM FROM plan ORDER BY NOM";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            nom.add(rs.getString("NOM"));
        }
        return nom;
    }



}


