package tn.esprit.models;

public class coach {
    private int id,age,evaluation;
    private float tarif;
    private String nom,prenom,photo_url,biographie,specialite;
    private boolean disponibilite;

    public coach(int id, int age, int evaluation, float tarif, String nom, String prenom, String photo_url, String biographie, String specialite) {
        this.id = id;
        this.age = age;
        this.evaluation = evaluation;
        this.tarif = tarif;
        this.nom = nom;
        this.prenom = prenom;
        this.photo_url = photo_url;
        this.biographie = biographie;
        this.specialite = specialite;
    }
    public coach(){

    }

    public coach(int age, int evaluation, float tarif, String nom, String prenom, String photo_url, String biographie, String specialite) {
        this.age = age;
        this.evaluation = evaluation;
        this.tarif = tarif;
        this.nom = nom;
        this.prenom = prenom;
        this.photo_url = photo_url;
        this.biographie = biographie;
        this.specialite = specialite;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }


    @Override
    public String toString() {
        return "coach{" +
                "id=" + id +
                ", age=" + age +
                ", evaluation=" + evaluation +
                ", tarif=" + tarif +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", biographie='" + biographie + '\'' +
                ", specialite='" + specialite + '\'' +

                '}';
    }
}


