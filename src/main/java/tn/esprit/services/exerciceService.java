package tn.esprit.services;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class exerciceService implements IService<exercice> {

    private Connection connection;
    public exerciceService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(exercice exercice) throws SQLException {
        String req = "INSERT INTO exercice (NOM,DESCRIPTION,MUSCLE_CIBLE,IMAGE_URL) VALUES('"+exercice.getNOM()+"','"+exercice.getDESCRIPTION()+"','"+exercice.getMUSCLE_CIBLE()+"','"+exercice.getIMAGE_URL()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void ajouterPlan(plan plan, List<exercice> exercices) throws SQLException {

    }

    @Override
    public void modifier(exercice exercice) throws SQLException {
        String req = "UPDATE exercice SET NOM=?, DESCRIPTION=?,MUSCLE_CIBLE =? WHERE IdExercice=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,exercice.getNOM());
        ps.setString(2,exercice.getDESCRIPTION());
        ps.setString(3,exercice.getMUSCLE_CIBLE());
        ps.setInt(4,exercice.getID());
        ps.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM exercice WHERE IdExercice = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public List<exercice> recuperer() throws SQLException {
        List<exercice> exercices = new ArrayList<>();

        String req = "SELECT * FROM exercice ";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            exercice exercice = new exercice();
            exercice.setID(rs.getInt("IdExercice"));
            exercice.setNOM(rs.getString("NOM"));
            exercice.setDESCRIPTION(rs.getString("DESCRIPTION"));
            exercice.setMUSCLE_CIBLE(rs.getString("MUSCLE_CIBLE"));
            exercice.setIMAGE_URL(rs.getString("IMAGE_URL"));
            exercices.add(exercice);
        }
        return exercices;
    }
    // Méthode pour récupérer un exercice par son ID
    public exercice recupererExerciceParId(int id)throws SQLException {
        String query = "SELECT * FROM exercice WHERE idExercice = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exercice exercice = new exercice();
                exercice.setID(resultSet.getInt("idExercice"));
                exercice.setNOM(resultSet.getString("NOM"));
                exercice.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
                exercice.setDESCRIPTION(resultSet.getString("MUSCLE_CIBLE"));
                return exercice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si aucun exercice correspondant n'est trouvé
    }
    public void deletePlanExerciceByExerciceId(int id) throws SQLException {
        String req = "DELETE FROM plan_exercice WHERE IdExercice = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public List<String> getUniqueMuscleCibles() throws SQLException {
        List<String> muscleCibles = new ArrayList<>();
        String req = "SELECT DISTINCT MUSCLE_CIBLE FROM exercice ORDER BY MUSCLE_CIBLE";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            muscleCibles.add(rs.getString("MUSCLE_CIBLE"));
        }
        return muscleCibles;
    }

    public List<exercice> rechercherParNom(String nom) throws SQLException {
        List<exercice> exercices = new ArrayList<>();
        String query = "SELECT * FROM exercice WHERE NOM LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + nom + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                exercice exercice = new exercice();
                exercice.setNOM(resultSet.getString("NOM"));
                exercice.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
                exercice.setMUSCLE_CIBLE(resultSet.getString("MUSCLE_CIBLE"));
                // If other columns are present, you can add them in the same way
                exercices.add(exercice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercices; // Return the list of matching plans
    }
    public List<String> getUniqueMuscleTargets() throws SQLException {
        List<String> muscleTargets = new ArrayList<>();
        String req = "SELECT DISTINCT MUSCLE_CIBLE FROM exercice ORDER BY MUSCLE_CIBLE";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            muscleTargets.add(rs.getString("MUSCLE_CIBLE"));
        }
        return muscleTargets;
    }
}
