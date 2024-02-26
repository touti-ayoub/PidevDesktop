package tn.esprit.services;

import tn.esprit.models.Competition;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ICompetitionService <C>{
//
    void ajouter (C c) throws SQLException;
    void modifier (C c) throws SQLException;
    void supprimer(int id) throws SQLException;

    List<C> recuperer() throws SQLException;
    // List<C> rechercherParDate(Date dateDebut, Date dateFin) throws SQLException ;
     Competition recupererParId(int codeC) throws SQLException;
     List<Competition> rechercherParLibelle(String libelleRecherche) throws SQLException ;

    }
