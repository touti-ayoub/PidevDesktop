package tn.esprit.services;


import tn.esprit.models.User;
import tn.esprit.controllers.AlertsController;

import tn.esprit.utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IServiceUser {

    private Connection con;;
    Statement stm;

    public UserService() {
        con = MyDatabase.getInstance().getConnection();
    }


    @Override
    public boolean AjouterUser(User u) {
        try {
            String req = "INSERT INTO `user` (`role`, `password`, `nom`, `prenom`, `email`, `token`, `activated`, `username`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(req);
            pstm.setString(1, u.getRole());
            pstm.setString(2, u.getPassword());
            pstm.setString(3, u.getNom());
            pstm.setString(4, u.getPrenom());
            pstm.setString(5, u.getEmail());
            pstm.setString(6, u.getToken());
            pstm.setInt(7, u.getActivated());
            pstm.setString(8, u.getUsername());
            pstm.executeUpdate();

            System.out.println("User Ajouté");
            return true;

        } catch (SQLException ex) {
            System.out.println("Erreur d'ajout du user");
            return false;

        }
    }
    @Override
    public boolean ModifierUser(User u) {
        try {
            String req = "UPDATE user SET  role=? , password=? , nom=? , prenom=? , email=? , token=? , activated=?, username=? WHERE id=?";
            PreparedStatement pstm = con.prepareStatement(req);
            pstm.setString(1, u.getRole());
            pstm.setString(2, u.getPassword());
            pstm.setString(3, u.getNom());
            pstm.setString(4, u.getPrenom());
            pstm.setString(5, u.getEmail());
            pstm.setString(6, u.getToken());
            pstm.setInt(7, u.getActivated());
            pstm.setString(8, u.getUsername());
            pstm.setInt(9, u.getId());
            pstm.executeUpdate();


            System.out.println("User Modifié "+u.getId());
            return true;
        } catch (SQLException ex) {
            System.out.println("Erreur de modification du user");
            return false;
        }

    }

    @Override
    public boolean SupprimerUser(int id) {
        try {
            String req = "DELETE FROM `user` WHERE id = " + id + "";
            PreparedStatement pstm = con.prepareStatement(req);
            pstm.executeUpdate();
            System.out.println("User Supprimé");
            return true;
        } catch (SQLException ex) {
            System.out.println("Erreur de supression du user");
        }
        return false;

    }

    @Override
    public List<User> AfficherUsers() {
        List<User> users = new ArrayList();
        try {
            String req = "SELECT * FROM user";
            PreparedStatement pstm = con.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setToken(rs.getString("token"));
                user.setActivated(rs.getInt("activated"));
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("id"));

                users.add(user);
            }
        } catch (SQLException ex) {

            System.out.println("Erreur d'affichage des users");
        }
        return users;
    }



    @Override
    public boolean Register(User u) {

        try {
            String req = "SELECT * FROM user WHERE username=\""+u.getUsername()+"\";";
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next())
            {
                AlertsController.get().Alert(".","Erreur","Username existe déja!");
                return false;

            }

        } catch (SQLException ex) {

            System.out.println("Erreur Register");
        }

        try {
            String req = "INSERT INTO `user` (`role`, `password`, `nom`, `prenom`, `email`, `token`, `activated`, `username`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(req);
            pstm.setString(1, u.getRole());
            pstm.setString(2, u.getPassword());
            pstm.setString(3, u.getNom());
            pstm.setString(4, u.getPrenom());
            pstm.setString(5, u.getEmail());
            pstm.setString(6, u.getToken());
            pstm.setInt(7, u.getActivated());
            pstm.setString(8, u.getUsername());
            pstm.executeUpdate();

            AlertsController.get().Alert("information","Succès","Compte Créer!");
            System.out.println("User Ajouté");
            return true;

        } catch (SQLException ex) {

            System.out.println("Erreur d'ajout du user");
            return false;
        }

    }

    @Override
    public User RecupererUser(String username) {
        User user = new User();
        try {
            String req = "SELECT * FROM user WHERE username=\""+username+"\";";
            PreparedStatement pstm = con.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setToken(rs.getString("token"));
                user.setActivated(rs.getInt("activated"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException ex) {

            System.out.println("Erreur de récuperation");
        }
        return user;

    }

    @Override
    public User RecupererUser(int id) {

        User user = new User();
        try {
            String req = "SELECT * FROM user WHERE id=\""+id+"\";";
            PreparedStatement pstm = con.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setToken(rs.getString("token"));
                user.setActivated(rs.getInt("activated"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException ex) {

            System.out.println("Erreur de récuperation");
        }
        return user;

    }

    @Override
    public User RecupererUserEmail(String email) {

        User user = new User();
        try {
            String req = "SELECT * FROM user WHERE email=\""+email+"\";";
            PreparedStatement pstm = con.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setToken(rs.getString("token"));
                user.setActivated(rs.getInt("activated"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException ex) {

            System.out.println("Erreur de récuperation");
        }
        return user;

    }



}
