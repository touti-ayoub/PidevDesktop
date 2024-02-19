package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IParticipationService <P>{
    void ajouterParticipation( P p) throws SQLException;
    void modifier(P p) throws SQLException;


    /*
     String req = "DELETE FROM competition WHERE codeC=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, code);
            ps.executeUpdate();*/
    void supprimer(int id) throws SQLException;

    List<P> recuperer() throws SQLException;
}
