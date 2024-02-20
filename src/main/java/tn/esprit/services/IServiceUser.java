package tn.esprit.services;

import tn.esprit.models.User;

import java.util.List;

public interface IServiceUser {
    boolean AjouterUser(User u);


    boolean ModifierUser(User user);

    boolean SupprimerUser(int id);

    List<User> AfficherUsers();



    boolean Register(User user);

    User RecupererUser(String username);
    User RecupererUser(int id);
    User RecupererUserEmail(String email);
}
