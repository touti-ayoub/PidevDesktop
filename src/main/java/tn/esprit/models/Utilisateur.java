package tn.esprit.models;

import java.util.Objects;

public class Utilisateur {
    private int codeU;
private String prenom;

    public Utilisateur() {
    }

    public Utilisateur(int codeU) {
        this.codeU = codeU;
    }

    public int getCodeU() {
        return codeU;
    }

    public void setCodeU(int codeU) {
        this.codeU = codeU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return codeU == that.codeU;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeU);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "codeU=" + codeU +
                '}';
    }
}
