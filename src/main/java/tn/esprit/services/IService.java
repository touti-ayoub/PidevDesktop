package tn.esprit.services;
import tn.esprit.models.exercice;
import tn.esprit.models.plan;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException;
    void ajouterPlan(plan plan, List<exercice> exercices)throws SQLException;

    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;


}
