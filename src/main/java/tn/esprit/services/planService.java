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
         //   st.executeUpdate(req);
        //}



    @Override
    public void ajouter(plan plan) throws SQLException {

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
        String req = "UPDATE plan SET NOM=?, DESCRIPTION=? WHERE IdPlan=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,plan.getNOM());
        ps.setString(2,plan.getDESCRIPTION());
        ps.setInt(3,plan.getID());
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
                plans.add(plan);
            }
            return plans;
        }
    public plan rechercherParNom(String nom) throws SQLException {
        String query = "SELECT * FROM plan WHERE NOM = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                plan plan = new plan();
                plan.setID(resultSet.getInt("IdPlan"));
                plan.setNOM(resultSet.getString("NOM"));
                plan.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
                // If other columns are present, you can add them in the same way
                return plan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching plan is found
    }
    }


