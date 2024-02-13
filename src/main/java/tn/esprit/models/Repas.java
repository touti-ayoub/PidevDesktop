package tn.esprit.models;

public class Repas {

    private int id, quantite;
    private String aliment;

    public Repas(int id, int quantite, String aliment) {
        this.id = id;
        this.quantite = quantite;
        this.aliment = aliment;
    }

    public Repas(int quantite, String aliment) {
        this.quantite = quantite;
        this.aliment = aliment;
    }

    public Repas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getAliment() {
        return aliment;
    }

    public void setAliment(String aliment) {
        this.aliment = aliment;
    }

    @Override
    public String toString() {
        return "Repas{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", aliment='" + aliment + '\'' +
                '}';
    }
}
