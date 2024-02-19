package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface ICompetitionService <C>{
//
    void ajouter (C c) throws SQLException;
    void modifier (C c) throws SQLException;
    void supprimer(int id) throws SQLException;

    List<C> recuperer() throws SQLException;

}
