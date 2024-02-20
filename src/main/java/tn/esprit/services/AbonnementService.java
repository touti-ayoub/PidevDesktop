package tn.esprit.services;

import tn.esprit.models.Abonnement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IServiceAbonnement {

    private Connection con;

    @Override
    public boolean ajouterAbonnement(Abonnement a) {
        try {

            String req = "INSERT INTO abonnement (nomH,type,adresse)" + " VALUES (?,?,?)";
            PreparedStatement prst = con.prepareStatement(req);

            prst.setString(1, a.getNomH());
            prst.setString(2, a.getType());
            prst.setString(3, a.getAdresse());


            prst.executeUpdate();
            System.out.println("abonnement Ajouté");
            return true;

        } catch (SQLException ex) {
            System.out.println("Erreur d'ajout du l'abonnement");
            return false;

        }


        @Override
        public boolean supprimerAbonnement ( int id){
            String delete = "DELETE FROM abonnement where id= ?";
            try {
                PreparedStatement prst = con.prepareStatement(delete);
                prst.setLong(1, id);
                prst.executeUpdate();
                System.out.println("suppression abonnement avec succées");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<Abonnement> afficherAbonnement() {
        List<Abonnement> myList = new ArrayList<>();
        ;
        try {

            String select = "SELECT * FROM `Abonnement`";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(select);

            while (rs.next()) {
                {
                    Abonnement a = new Abonnement();
                    a.setIdH(rs.getInt(1));
                    a.setNomH(rs.getString("nomH"));
                    a.setType(rs.getString("type"));
                    a.setAdresse(rs.getString("adresse"));


                    myList.add(a);
                }
                System.out.println("affichage succées");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return myList;
    }

    @Override
    public boolean modifierAbonnement(Abonnement abonnement) {
        return false;
    }
}
/*
        @Override
        public boolean modifierAbonnement (Abonnement a){
            try {
                String req = "UPDATE Abonnement SET  nomH=? , type=? , adresse=?   WHERE id=?";
                PreparedStatement pstm = con.prepareStatement(req);
                pstm.setString(1, a.get());
                pstm.setString(2, a.getType());
                pstm.setString(3, a.getAdresse());

                pstm.setInt(5, a.getId());
                pstm.executeUpdate();
                System.out.println("Abonnement Modifié");
                return true;
            } catch (SQLException ex) {

                System.out.println("Erreur de modification du Abonnement ");
                return false;
            }
        }
    }
