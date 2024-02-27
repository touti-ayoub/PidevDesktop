package tn.esprit.models;

//import java.sql.Date;
import java.sql.Date;
import java.util.Objects;

public class Competition {
    private int codeC;
    //
    private String libelle;
    private Date dateDebut;
    private Date dateFin;

    private float tarif;


    public Competition() {
    }

    public Competition(String libelle, Date dateDebut, Date dateFin, float tarif) {
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarif = tarif;
    }

    public Competition(int codeC, String libelle, Date dateDebut, Date dateFin, float tarif) {
        this.codeC = codeC;
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;

        this.tarif = tarif;
    }

    public int getCodeC() {
        return codeC;
    }

    public void setCodeC(int codeC) {
        this.codeC = codeC;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

  /*  public int getNbrMembre() {
        return nbrMembre;
    }

    public void setNbrMembre(int nbrMembre) {
        this.nbrMembre = nbrMembre;
    }*/

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return codeC == that.codeC  && tarif == that.tarif && Objects.equals(libelle, that.libelle) && Objects.equals(dateDebut, that.dateDebut) && Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeC, libelle, dateDebut, dateFin, tarif);
    }

    @Override
    public String toString() {
        return "Competition: " +

                libelle  +
                "   dateDebut: " + dateDebut +
                "   dateFin: " + dateFin +

                "   tarif: " + tarif;
    }
}
