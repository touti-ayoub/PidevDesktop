package tn.esprit.models;

public class exercice {
    private int ID;
    private String NOM, DESCRIPTION, MUSCLE_CIBLE, IMAGE_URL;
    public exercice(int ID, String NOM, String DESCRIPTION ,String MUSCLE_CIBLE, String IMAGE_URL) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.MUSCLE_CIBLE = MUSCLE_CIBLE;
        this.IMAGE_URL = IMAGE_URL;
    }

    public exercice(String NOM, String DESCRIPTION, String MUSCLE_CIBLE , String IMAGE_URL) {
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.MUSCLE_CIBLE = MUSCLE_CIBLE;
        this.IMAGE_URL = IMAGE_URL;
    }


    public exercice(String NOM, String DESCRIPTION) {
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
    }

    public exercice() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }


    public String getMUSCLE_CIBLE() {
        return MUSCLE_CIBLE;
    }

    public void setMUSCLE_CIBLE(String MUSCLE_CIBLE) {
        this.MUSCLE_CIBLE = MUSCLE_CIBLE;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    @Override
    public String toString() {
        return "exercice{" +
                "ID=" + ID +
                ", NOM='" + NOM + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", MUSCLE_CIBLE='" + MUSCLE_CIBLE + '\'' +
                ", IMAGE_URL='" + IMAGE_URL + '\'' +
                '}';
    }
}

