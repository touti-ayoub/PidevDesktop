package tn.esprit.services;

import tn.esprit.models.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface IUtilisateurService<U> {
    List<U> recuperer() throws SQLException;


    Utilisateur recupererParId(int codeU) throws SQLException;
    public void modifier(Utilisateur utilisateur) throws SQLException ;

    }
