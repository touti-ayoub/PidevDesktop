package tn.esprit.models;

public class Produit {

    private int id;
    private String nom;
    private String type;
    private String taille;
    private float prix;
    private String photo;

    public Produit(String proteine, String liquide, String small) {
    }

    public Produit(int id, String nom, String type, String taille, float prix, String photo) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.taille = taille;
        this.prix = prix;
        this.photo = photo;
    }

    public Produit(String nom, String type, String taille, float prix, String photo) {
        this.nom = nom;
        this.type = type;
        this.taille = taille;
        this.prix = prix;
        this.photo = photo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", type=" + type + ", taille=" + taille + ", prix=" + prix + ", photo=" + photo + '}';
    }


}
