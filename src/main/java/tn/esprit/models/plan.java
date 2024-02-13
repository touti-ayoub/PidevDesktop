package tn.esprit.models;

public class plan {
    private int id;
    private String nom,description,duree,objectif;

    public plan(int id, String nom, String description, String duree, String objectif) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.objectif = objectif;
    }

    public plan(String nom, String description, String duree, String objectif) {
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.objectif = objectif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "plan{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", duree='" + duree + '\'' +
                ", objectif='" + objectif + '\'' +
                '}';
    }
}
