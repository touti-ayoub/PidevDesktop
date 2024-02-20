package tn.esprit.services;

import tn.esprit.models.Abonnement;

import java.util.List;

public class AbonnementService implements IServiceAbonnement{
    @Override
    public boolean ajouterAbonnement(Abonnement abonnement) {
        return false;
    }

    @Override
    public boolean supprimerAbonnement(int id) {
        return false;
    }

    @Override
    public List<Abonnement> afficherAbonnement() {
        return null;
    }

    @Override
    public boolean modifierAbonnement(Abonnement abonnement) {
        return false;
    }
}
