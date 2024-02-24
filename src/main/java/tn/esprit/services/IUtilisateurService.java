package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IUtilisateurService<U> {
    List<U> recuperer() throws SQLException;


}
