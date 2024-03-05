package tn.esprit.services;

import tn.esprit.models.Participation;

import java.sql.SQLException;
import java.util.List;

public interface IParticipationService <P>{
    void ajouterParticipation( P p) throws SQLException;
    void modifier(P p) throws SQLException;



    void supprimer(int id) throws SQLException;

    List<P> recuperer() throws SQLException;


    //les demandes
    List<Participation> recupererAccepte() throws SQLException;
     List<Participation> recupererRefuser() throws SQLException ;


        /* float getTarifReduitSemestreAnnee(int codeUtilisateur,  float tarif) throws SQLException;*/
}
