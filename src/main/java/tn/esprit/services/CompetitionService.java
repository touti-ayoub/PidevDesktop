package tn.esprit.services;

import tn.esprit.models.Competition;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitionService implements ICompetitionService<Competition>{

    private Connection connection;

    public CompetitionService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Competition competition) throws SQLException {
        String req = "INSERT INTO competition (libelle,dateDebut,dateFin,nbrMembre,nbrMaxMembres) Values('" + competition.getLibelle() + "','" + competition.getDateDebut() + "','" + competition.getDateFin() + "','" + competition.getNbrMembre() + "'," + competition.getNbrMaxMembres() + ")";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Competition competition) throws SQLException {
        String req = "UPDATE competition SET libelle=?, dateDebut=? , dateFin=?, nbrMembre=?, nbrMaxMembres=? WHERE codeC=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, competition.getLibelle());
        ps.setDate(2, competition.getDateDebut());
        ps.setDate(3, competition.getDateFin());
        ps.setInt(4, competition.getNbrMembre());
        ps.setInt(5, competition.getNbrMaxMembres());
        ps.setInt(6, competition.getCodeC());
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
            c.setNbrMembre(rs.getInt("nbrMembre"));
            c.setNbrMaxMembres(rs.getInt("nbrMaxMembres"));
            competitions.add(c);
        }
        return competitions;

    }



}
