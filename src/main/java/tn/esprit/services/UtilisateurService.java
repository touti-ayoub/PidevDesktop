package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
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

    @Override
    public Utilisateur recupererParId(int codeU) throws SQLException {
        // Implementez la logique pour récupérer un utilisateur par son ID depuis la base de données
        String query = "SELECT * FROM utilisateur WHERE codeU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, codeU);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérez les détails de l'utilisateur depuis le résultat
                    String prenom = resultSet.getString("prenom");
                    // Ajoutez d'autres champs selon votre modèle Utilisateur

                    // Créez un nouvel objet Utilisateur
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setCodeU(codeU);
                    utilisateur.setPrenom(prenom);
                    // Définissez d'autres champs selon votre modèle Utilisateur

                    return utilisateur;
                }
            }
        }

        // Retournez null si aucun utilisateur n'est trouvé avec l'ID donné
        return null;
    }
}
