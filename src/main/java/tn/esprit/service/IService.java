package tn.esprit.service;

import tn.esprit.models.Produit;

import java.util.List;

public interface IService<T>{
   Void ajouter(T t);

    void modifier(T t);

    void supprimer(int id);

    List<T> recuperer();
}

