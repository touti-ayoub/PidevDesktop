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
    /*    @Override
        public void modifier(Competition competition) throws SQLException {
            String req = "UPDATE competition SET libelle=?, dateDebut=? , dateFin=?, nbrMembre=?, nbrMaxMembres=? WHERE code=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, competition.getLibelle());
            ps.setDate(2, competition.getDateDebut());
            ps.setDate(3, competition.getDateFin());
            ps.setInt(4, competition.getNbrMembre());
            ps.setInt(5, competition.getNbrMaxMembres());
            ps.setInt(6, competition.getCode());
            ps.executeUpdate();
        }*/
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
/*
 String req = "DELETE FROM competition WHERE codeC=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, code);
        ps.executeUpdate();*/
    @Override
    public void supprimer(int code) throws SQLException {
   String req = "DELETE FROM participation WHERE codeP=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, code);
        ps.executeUpdate();
    }
/*  List<Competition> competitions = new ArrayList<>();
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
*/
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
}
