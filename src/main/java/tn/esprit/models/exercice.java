package tn.esprit.models;

public class exercice {
    private int ID;
    private String NOM, DESCRIPTION, IMAGE_URL;
    public exercice(int ID, String NOM, String DESCRIPTION) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
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

    @Override
    public String toString() {
        return "exercice{" +
                "ID=" + ID +
                ", NOM='" + NOM + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                '}';
    }
}