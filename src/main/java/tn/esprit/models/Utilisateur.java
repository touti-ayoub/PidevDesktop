package tn.esprit.models;

import java.util.Objects;

public class Utilisateur {
    private int codeU;
    //
private String prenom;
private int nbrParticipation;

    public Utilisateur() {
    }

    public Utilisateur(String prenom) {
        this.prenom = prenom;
    }

    public Utilisateur(int codeU, String prenom) {
        this.codeU = codeU;
        this.prenom = prenom;
    }

    public Utilisateur(int codeU, String prenom, int nbrParticipation) {
        this.codeU = codeU;
        this.prenom = prenom;
        this.nbrParticipation = nbrParticipation;
    }

    public int getCodeU() {
        return codeU;
    }

    public void setCodeU(int codeU) {
        this.codeU = codeU;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNbrParticipation() {
        return nbrParticipation;
    }

    public void setNbrParticipation(int nbrParticipation) {
        this.nbrParticipation = nbrParticipation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return codeU == that.codeU && nbrParticipation == that.nbrParticipation && Objects.equals(prenom, that.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeU, prenom, nbrParticipation);
    }

    @Override
    public String toString() {
        return "Utilisateur: "+ prenom ;}
}
