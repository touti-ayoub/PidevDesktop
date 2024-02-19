package tn.esprit.models;

public class plan {
    //objectif du plan
    private int ID;
    private String NOM, DESCRIPTION;

    public plan(int ID, String NOM, String DESCRIPTION) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
    }

    public plan(String NOM, String DESCRIPTION) {
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
    }

    public plan() {

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
        return "plan{" +
                "ID=" + ID +
                ", NOM='" + NOM + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                '}';
    }
}