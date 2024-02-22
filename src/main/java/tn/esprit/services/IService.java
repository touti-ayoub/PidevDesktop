package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;
    int modifier(T t) throws SQLException;
    boolean supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
}
