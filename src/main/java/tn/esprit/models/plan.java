package tn.esprit.models;

public class plan {
    //objectif du plan
    private int ID;

    public plan(int ID, String NOM, String DESCRIPTION, String IMAGE_URL) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.IMAGE_URL = IMAGE_URL;
    }

    private String NOM, DESCRIPTION,IMAGE_URL;


    public plan(String NOM, String DESCRIPTION,String IMAGE_URL) {
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.IMAGE_URL=IMAGE_URL;
    }

    public plan() {

    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
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