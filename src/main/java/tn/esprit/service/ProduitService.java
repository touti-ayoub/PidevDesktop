package tn.esprit.service;

import tn.esprit.models.Produit;
import tn.esprit.utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProduitService implements IService<Produit> {
    private static final Logger LOGGER =;
    private Connection connection;

    public ProduitService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "INSERT INTO produit(nom, type, taille) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getType());
            ps.setString(3, produit.getTaille());
            ps.executeUpdate();
            LOGGER.info("Produit ajouté avec succès: " + produit);
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de l'ajout du produit: " + e.getMessage());
            throw e; // rethrow the exception after logging
        }
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "UPDATE produit SET nom=?, type=?, taille=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getType());
            ps.setInt(3, produit.getTaille());
            ps.setInt(4, produit.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM produit WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Produit> recuperer() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Produit produit = new Produit("proteine", "liquide", "small");
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
                produit.setType(rs.getString("type"));
                produit.setTaille(rs.getInt("taille"));

                produits.add(produit);
            }
        }
        return produits;
    }
}
