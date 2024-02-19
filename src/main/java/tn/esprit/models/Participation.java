package tn.esprit.models;

import java.util.Objects;

public class Participation {
    private int codeP;
    //
    private int codeU;
    private int codeC;
    private String description;

    public Participation() {
    }

    public Participation(int codeU, int codeC, String description) {
        this.codeU = codeU;
        this.codeC = codeC;
        this.description = description;
    }

    public Participation(int codeP, int codeU, int codeC, String description) {
        this.codeP = codeP;
        this.codeU = codeU;
        this.codeC = codeC;
        this.description = description;
    }

    public int getCodeP() {
        return codeP;
    }

    public void setCodeP(int codeP) {
        this.codeP = codeP;
    }

    public int getCodeU() {
        return codeU;
    }

    public void setCodeU(int codeU) {
        this.codeU = codeU;
    }

    public int getCodeC() {
        return codeC;
    }

    public void setCodeC(int codeC) {
        this.codeC = codeC;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return codeP == that.codeP && codeU == that.codeU && codeC == that.codeC && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeP, codeU, codeC, description);
    }

    @Override
    public String toString() {
        return "Participation{" +
                "codeP=" + codeP +
                ", codeU=" + codeU +
                ", codeC=" + codeC +
                ", description='" + description + '\'' +
                '}';
    }
}
