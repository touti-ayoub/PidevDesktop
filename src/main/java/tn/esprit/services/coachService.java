package tn.esprit.services;

import tn.esprit.models.coach;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class coachService implements IService<coach>{
    private Connection connection;
    public coachService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(coach coach) throws SQLException {
        String req = "INSERT INTO coach (nom,prenom,age,photo_url,biographie,specialite,tarif,evaluation) VALUES('"+coach.getNom()+"','"+coach.getPrenom()+"',"+coach.getAge()+",'"+coach.getPhoto_url()+"','"+coach.getBiographie()+"','"+coach.getSpecialite()+"',"+coach.getTarif()+",'"+coach.getEvaluation()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(coach coach) throws SQLException {
        String req = "UPDATE coach SET nom=?, prenom=?, age=? , photo_url=?, biographie=? , specialite =?,tarif =?,evaluation =? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,coach.getNom());
        ps.setString(2,coach.getPrenom());
        ps.setInt(3,coach.getAge());
        ps.setString(4,coach.getPhoto_url());
        ps.setString(5,coach.getBiographie());
        ps.setString(6,coach.getSpecialite());
        ps.setFloat(7,coach.getTarif());
        ps.setInt(8,coach.getEvaluation());
        ps.setInt(9,coach.getId());
        ps.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM coach WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();

    }

    @Override
    public List<coach> recuperer() throws SQLException {
        List<coach> coachs = new ArrayList<>();

        String req = "SELECT * FROM coach ";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            coach coach = new coach();
            coach.setId(rs.getInt("id"));
            coach.setNom(rs.getString("nom"));
            coach.setPrenom(rs.getString("prenom"));
            coach.setAge(rs.getInt("age"));
            coach.setPhoto_url(rs.getString("photo_url"));
            coach.setBiographie(rs.getString("biographie"));
            coach.setSpecialite(rs.getString("specialite"));
            coach.setTarif(rs.getFloat("tarif"));

            coachs.add(coach);
        }
 return coachs;
    }
}
