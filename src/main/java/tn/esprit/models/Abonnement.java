package tn.esprit.models;

public class Abonnement {
   private int idH;
   private String type;
   private String nomH;
   private String adresse;

    public Abonnement(int idH, String type, String nomH, String adresse) {
        this.idH = idH;
        this.type = type;
        this.nomH = nomH;
        this.adresse = adresse;
    }

    public Abonnement(String type, String nomH, String adresse) {
        this.type = type;
        this.nomH = nomH;
        this.adresse = adresse;
    }

    public int getIdH() {
        return idH;
    }

    public void setIdH(int idH) {
        this.idH = idH;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNomH() {
        return nomH;
    }

    public void setNomH(String nomH) {
        this.nomH = nomH;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "idH=" + idH +
                ", type='" + type + '\'' +
                ", nomH='" + nomH + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
