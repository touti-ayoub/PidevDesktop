package tn.esprit.services;

import tn.esprit.models.Competition;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitionService implements ICompetitionService<Competition>{

    private Connection connection;
//
    public CompetitionService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Competition competition) throws SQLException {
        String req = "INSERT INTO competition (libelle,dateDebut,dateFin,nbrMaxMembres) Values('" + competition.getLibelle() + "','" + competition.getDateDebut() + "','" + competition.getDateFin()  + "'," + competition.getNbrMaxMembres() + ")";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Competition competition) throws SQLException {
        String req = "UPDATE competition SET libelle=?, dateDebut=? , dateFin=?, nbrMaxMembres=? WHERE codeC=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, competition.getLibelle());
        ps.setDate(2, competition.getDateDebut());
        ps.setDate(3, competition.getDateFin());
        ps.setInt(4, competition.getNbrMaxMembres());
        ps.setInt(5, competition.getCodeC());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int code) throws SQLException {
        String req = "DELETE FROM competition WHERE codeC=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, code);
        ps.executeUpdate();
    }

    @Override
    public List<Competition> recuperer() throws SQLException {
        List<Competition> competitions = new ArrayList<>();
        String req = "SELECT * FROM competition";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Competition c = new Competition();
            c.setCodeC(rs.getInt("codeC"));
            c.setLibelle(rs.getString("libelle"));
            c.setDateDebut(rs.getDate("dateDebut"));
            c.setDateFin(rs.getDate("dateFin"));
       /*     c.setNbrMembre(rs.getInt("nbrMembre"));*/
            c.setNbrMaxMembres(rs.getInt("nbrMaxMembres"));
            competitions.add(c);
        }
        return competitions;

    }
public Competition recupererParId(int codeC) throws SQLException {
        // Implement the logic to retrieve a competition by its ID from the database
        String query = "SELECT * FROM competition WHERE codeC = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, codeC);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                    // Retrieve competition details from the result set
                    String libelle = resultSet.getString("libelle");
                    java.sql.Date dateDebut = resultSet.getDate("dateDebut");
                    java.sql.Date dateFin = resultSet.getDate("dateFin");
                    int nbrMaxMembres = resultSet.getInt("nbrMaxMembres");

                    // Create a new Competition object
                    Competition competition = new Competition(codeC, libelle, dateDebut, dateFin, nbrMaxMembres);

                    return competition;
                }
            }
        }

        // Return null if no competition is found with the given ID
        return null;
    }

/*
    public List<Competition> rechercherParDate(Date dateDebut, Date dateFin) throws SQLException {
        List<Competition> competitions = new ArrayList<>();
        String req = "SELECT * FROM competition WHERE dateDebut >= ? AND dateFin <= ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setDate(1, new java.sql.Date(dateDebut.getTime()));
            ps.setDate(2, new java.sql.Date(dateFin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Competition c = new Competition();
                    c.setCodeC(rs.getInt("codeC"));
                    c.setLibelle(rs.getString("libelle"));
                    c.setDateDebut(rs.getString("dateDebut"));
                    c.setDateFin(rs.getString("dateFin"));
                    c.setNbrMembre(rs.getInt("nbrMembre"));
                    c.setNbrMaxMembres(rs.getInt("nbrMaxMembres"));
                    competitions.add(c);
                }
            }
        }
        return competitions;
    }
*/


}
