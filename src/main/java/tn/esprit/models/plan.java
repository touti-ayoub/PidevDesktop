package tn.esprit.models;

import java.util.ArrayList;
import java.util.List;

public class plan {


    //objectif du plan
    private int ID;
    private String NOM, DESCRIPTION,IMAGE_URL;

    private List<exercice> exercices;
    public plan(int ID, String NOM, String DESCRIPTION, String IMAGE_URL, List<exercice> exercices) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.IMAGE_URL = IMAGE_URL;
        this.exercices = exercices;
    }



    public plan(int ID, String NOM, String DESCRIPTION, String IMAGE_URL) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.IMAGE_URL = IMAGE_URL;
    }
    public plan(int ID, String NOM, String DESCRIPTION) {
        this.ID = ID;
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;

    }





    public plan(String NOM, String DESCRIPTION,String IMAGE_URL) {
        this.NOM = NOM;
        this.DESCRIPTION = DESCRIPTION;
        this.IMAGE_URL=IMAGE_URL;
    }

    public plan() {
        this.exercices = new ArrayList<>(); // Initialisation de la liste des exercices
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
    public List<exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<exercice> exercices) {
        this.exercices = exercices;
    }


    @Override
    public String toString() {
        return "Plan{" +
                "ID=" + ID +
                ", NOM='" + NOM + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", IMAGE_URL='" + IMAGE_URL + '\'' +
                ", exercices=" + exercices +
                '}';
    }
}