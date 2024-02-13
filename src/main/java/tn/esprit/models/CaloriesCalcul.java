package tn.esprit.models;

public class CaloriesCalcul {

    private int age, hauteur, poids;
    private String genre, activite;

    public CaloriesCalcul(int age, int hauteur, int poids, String genre, String activite) {
        this.age = age;
        this.hauteur = hauteur;
        this.poids = poids;
        this.genre = genre;
        this.activite = activite;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "CaloriesCalcul{" +
                "age=" + age +
                ", hauteur=" + hauteur +
                ", poids=" + poids +
                ", genre='" + genre + '\'' +
                ", activite='" + activite + '\'' +
                '}';
    }
}
