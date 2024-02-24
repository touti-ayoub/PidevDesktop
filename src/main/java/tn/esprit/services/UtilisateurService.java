package tn.esprit.services;

import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IUtilisateurService<Utilisateur> {
    private Connection connection;
    public UtilisateurService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public List<Utilisateur> recuperer() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        String req = "SELECT * FROM utilisateur";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Utilisateur u = new Utilisateur();
            u.setCodeU(rs.getInt("codeU"));
            u.setPrenom(rs.getString("prenom"));
            utilisateurs.add(u);
        }
        return utilisateurs;
    }
}
