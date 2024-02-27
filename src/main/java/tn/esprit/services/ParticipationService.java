package tn.esprit.services;

import tn.esprit.models.Participation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IParticipationService<Participation> {
    private Connection connection;
    public ParticipationService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouterParticipation(Participation participation) throws SQLException {
        String req = "INSERT INTO participation (codeU, codeC, `description`) VALUES ('" + participation.getCodeU() + "','" + participation.getCodeC() + "','" + participation.getDescription() + "')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }
    /*   modifier participant*/
    @Override
    public void modifier(Participation participation) throws SQLException {
        String req = "UPDATE participation SET codeU=?, codeC=? , description=? WHERE codeP=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, participation.getCodeU());
        ps.setInt(2, participation.getCodeC());
        ps.setString(3,participation.getDescription());
        ps.setInt(4, participation.getCodeP());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int code) throws SQLException {
   String req = "DELETE FROM participation WHERE codeP=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, code);
        ps.executeUpdate();
    }

    @Override
    public List<Participation> recuperer() throws SQLException{
         List<Participation> participations = new ArrayList<>();

        String req = "SELECT * FROM participation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Participation p = new Participation();
            p.setCodeP(rs.getInt("codeP"));
            p.setCodeU(rs.getInt("codeU"));
            p.setCodeC(rs.getInt("codeC"));
            p.setDescription(rs.getString("description"));
            participations.add(p);
        }
        return participations;
    }/*
    @Override
    public  float getTarifReduitSemestreAnnee(int codeUtilisateur, float tarif) throws SQLException {
        String query = "SELECT COUNT(*) AS nbrParticipations FROM participation " +
                "WHERE codeU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Paramètre : codeU
            preparedStatement.setInt(1, codeUtilisateur);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int nbrParticipations = resultSet.getInt("nbrParticipations");

                    // Appliquer la logique de calcul de réduction ici
                    float reduction = 0.0f;

                    if (nbrParticipations == 5) {
                        reduction = 0.1f; // 10% pour 5 participations
                    }

                    // Calculer le tarif réduit en fonction du tarif initial et de la réduction
                    float tarifReduit = tarif - (tarif * reduction);

                    return tarifReduit;
                }
            }
        }

        // Retournez le tarif initial si aucun utilisateur n'a de participation dans la période spécifiée
        return tarif;
    }*/

}
