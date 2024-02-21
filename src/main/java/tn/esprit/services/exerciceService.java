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
        String req = "UPDATE exercice SET NOM=?, DESCRIPTION=?,MUSCLE_CIBLE =? WHERE IdExerice=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,exercice.getNOM());
        ps.setString(2,exercice.getDESCRIPTION());
        ps.setString(3,exercice.getMUSCLE_CIBLE());
        ps.setInt(4,exercice.getID());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM exercice WHERE IDExercice = ?";
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
            exercice.setID(rs.getInt("idExercice"));
            exercice.setNOM(rs.getString("NOM"));
            exercice.setDESCRIPTION(rs.getString("DESCRIPTION"));
            exercice.setMUSCLE_CIBLE(rs.getString("MUSCLE_CIBLE"));
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
}
