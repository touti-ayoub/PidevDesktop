package tn.esprit.services;

import tn.esprit.models.Participation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParticipationService implements IParticipationService<Participation> {
    private Connection connection;
    public ParticipationService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouterParticipation(Participation participation) throws SQLException {


        // Calculer le tarif réduit
        float tarifReduit = calculerTarifReduit(participation.getCodeU());

        // Appliquer le tarif réduit au tarif initial
        float tarifApresReduit = participation.getCompetition().getTarif() * tarifReduit;
        String req = "INSERT INTO participation (codeU, codeC, description, tarifApresReduit, etatDemande) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, participation.getCodeU());
        ps.setInt(2, participation.getCodeC());
        ps.setString(3, participation.getDescription());
        ps.setFloat(4, tarifApresReduit);
        ps.setInt(5, participation.getEtatDemande()); // Ajoutez cette ligne pour le champ etatDemande

        ps.executeUpdate();


// Récupérer la clé générée automatiquement (codeP)
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            int codeP = generatedKeys.getInt(1);
            // Utiliser le constructeur avec tous les attributs pour créer la participation
            participation.setCodeP(codeP); // Mettre à jour le codeP de la participation
            participation.setTarifApresReduit(tarifApresReduit); // Mettre à jour le tarif après réduction        } else {
        }else {
            throw new SQLException("Échec de la création de la participation, aucun ID généré.");
        }
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
    }
    //les demandes
    @Override
    public List<Participation> recupererAccepte() throws SQLException {
        List<Participation> participations = new ArrayList<>();

        String req = "SELECT * FROM participation WHERE etatDemande = 2";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Participation p = new Participation();
            p.setCodeP(rs.getInt("codeP"));
            p.setCodeU(rs.getInt("codeU"));
            p.setCodeC(rs.getInt("codeC"));
            p.setDescription(rs.getString("description"));
            p.setTarifApresReduit(rs.getFloat("tarifApresReduit"));
            p.setEtatDemande(rs.getInt("etatDemande"));
            participations.add(p);
        }

        return participations;
    }

    public List<Participation> recupererRefuser() throws SQLException {
        List<Participation> participations = new ArrayList<>();

        String req = "SELECT * FROM participation WHERE etatDemande = 1";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Participation p = new Participation();
            p.setCodeP(rs.getInt("codeP"));
            p.setCodeU(rs.getInt("codeU"));
            p.setCodeC(rs.getInt("codeC"));
            p.setDescription(rs.getString("description"));
            p.setTarifApresReduit(rs.getFloat("tarifApresReduit"));
            p.setEtatDemande(rs.getInt("etatDemande"));
            participations.add(p);
        }

        return participations;
    }

    // Ajouter cette méthode pour calculer le tarif réduit
    private float calculerTarifReduit(int codeU) throws SQLException {
        // Récupérer les participations de l'utilisateur
        List<Participation> participationsUtilisateur = recupererParticipationsUtilisateur(codeU);
//System.out.println(participationsUtilisateur);
        int participationsDansMois = 0;
        int participationsDansTroisMois = 0;
        int participationsDansSixMois = 0;
        int participationsDansNeufMois = 0;
        int participationsDansAnnee = 0;

        // Date actuelle
        Date currentDate = new Date(System.currentTimeMillis());
//System.out.println("tawa"+currentDate);
        // Calculer le nombre de participations dans chaque période
        for (Participation participation : participationsUtilisateur) {
            Date dateFin = participation.getCompetition().getDateFin();
System.out.println(dateFin);
            if (isDateWithinMonths(dateFin, currentDate, 1)) {
  //              System.out.println("1");
                participationsDansMois++;
            }
            if (isDateWithinMonths(dateFin, currentDate, 3)) {
//                System.out.println("3");
                participationsDansTroisMois++;
            }
            if (isDateWithinMonths(dateFin, currentDate, 6)) {
  //              System.out.println("6");
                participationsDansSixMois++;
            }
            if (isDateWithinMonths(dateFin, currentDate, 9)) {
    //            System.out.println("9");
                participationsDansNeufMois++;
            }
            if (isDateWithinYear(dateFin, currentDate)) {
                participationsDansAnnee++;
            }
        }

        /// Appliquer la réduction en fonction du nombre de participations
        if (participationsDansMois >= 5) {
            System.out.println("Réduction de 50% appliquée (5 participations dans le mois).");
          //  System.out.println(participationsDansMois);



            return 0.5f; // 50% de réduction
        } else if (participationsDansTroisMois >= 5) {
            System.out.println("Réduction de 40% appliquée (5 participations dans les trois derniers mois).");
        //    System.out.println(participationsDansTroisMois);

            return 0.6f; // 40% de réduction
        } else if (participationsDansSixMois >= 5) {
            System.out.println("Réduction de 30% appliquée (5 participations dans les six derniers mois).");
      //     System.out.println(participationsDansSixMois);

            return 0.7f; // 30% de réduction
        } else if (participationsDansNeufMois >= 5) {
            System.out.println("Réduction de 20% appliquée (5 participations dans les neuf derniers mois).");
          // System.out.println(participationsDansNeufMois);

            return 0.8f; // 20% de réduction
        } else if (participationsDansAnnee >= 5) {
            System.out.println("Réduction de 10% appliquée (5 participations dans l'année).");
          //  System.out.println(participationsDansAnnee);

            return 0.9f; // 10% de réduction
        } else {
            System.out.println("Aucune réduction appliquée (moins de 5 participations).");

            return 1.0f; // Pas de réduction
        }
    }
    private boolean isDateWithinMonths(Date date, Date currentDate, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, -months);
        Date startDate = new Date(cal.getTimeInMillis());
        return date.after(startDate) && date.before(currentDate);
    }

    private boolean isDateWithinYear(Date date, Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.YEAR, -1);
        Date startDate = new Date(cal.getTimeInMillis());
        return date.after(startDate) && date.before(currentDate);
    }



    // Ajouter cette méthode pour récupérer les participations d'un utilisateur
    private List<Participation> recupererParticipationsUtilisateur(int codeU) throws SQLException {
        List<Participation> participationsUtilisateur = new ArrayList<>();

        String req = "SELECT * FROM participation WHERE codeU = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, codeU);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Participation p = new Participation();
            p.setCodeP(rs.getInt("codeP"));
            p.setCodeU(rs.getInt("codeU"));
            p.setCodeC(rs.getInt("codeC"));
            p.setDescription(rs.getString("description"));
            p.setTarifApresReduit(rs.getFloat("tarifApresReduit"));
            participationsUtilisateur.add(p);
        }

        return participationsUtilisateur;
    }
    public boolean modifierEtatDemande(int codeP, int newEtatDemande) throws SQLException {
        String req = "UPDATE participation SET etatDemande = ? WHERE codeP = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, newEtatDemande);
            ps.setInt(2, codeP);
            int rowsUpdated = ps.executeUpdate();

            // Retourne true si au moins une ligne a été mise à jour, sinon false
            return rowsUpdated > 0;
        }
    }

    public boolean verifierParticipationExistante(int codeU, int codeC) throws SQLException {
        String query = "SELECT COUNT(*) FROM participation WHERE codeU = ? AND codeC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, codeU);
            preparedStatement.setInt(2, codeC);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
