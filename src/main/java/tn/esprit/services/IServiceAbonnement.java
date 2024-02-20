package tn.esprit.services;

import tn.esprit.models.Abonnement;

import java.util.List;

public interface IServiceAbonnement {
    boolean ajouterAbonnement(Abonnement abonnement);
    boolean supprimerAbonnement(int id);
    List<Abonnement> afficherAbonnement();

    boolean modifierAbonnement(Abonnement abonnement);




}
