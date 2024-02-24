package tn.esprit.services;
import tn.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class plan_exercice {


        private Connection connection;

    public plan_exercice (){
        connection = MyDatabase.getInstance().getConnection();
    }

        public void afficherAssociations() throws SQLException {
            String query = "SELECT * FROM plan_exercice";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int EXERCICE_ID = resultSet.getInt("idPlan");
                    int PLAN_ID = resultSet.getInt("idExercice");
                    System.out.println("Association - Plan ID: " + EXERCICE_ID + ", Exercice ID: " + PLAN_ID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


