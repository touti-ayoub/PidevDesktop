package tn.esprit.services;

import tn.esprit.models.Repas;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepasService implements IService<Repas>{

    private Connection connection;

    public RepasService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Repas repas) throws SQLException {
        String req = "INSERT INTO repas (aliment,quantite) VALUES ('"+repas.getAliment()+"',"+repas.getQuantite()+")";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Repas repas) throws SQLException {
        String req ="UPDATE repas SET aliment = ?, quantite = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,repas.getAliment());
        ps.setInt(2,repas.getQuantite());
        ps.setInt(3,repas.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM repas WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public List<Repas> recuperer() throws SQLException {
        List <Repas> repass = new ArrayList<>();
        String req = "SELECT * FROM repas";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Repas repas = new Repas();
            repas.setId(rs.getInt("id"));
            repas.setAliment(rs.getString("aliment"));
            repas.setQuantite(rs.getInt("quantite"));
            repass.add(repas);
        }
        return repass;
    }
}
