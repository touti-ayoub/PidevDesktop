package tn.esprit.models;

import tn.esprit.services.CompetitionService;
import tn.esprit.services.UtilisateurService;

import java.sql.SQLException;
import java.util.Objects;

public class Participation {
    private int codeP;
    //
    private int codeU;
    private int codeC;
    private String description;

    public Participation() {
    }

    public Participation( int codeC, String description, int codeU) {
        this.codeU = codeU;
        this.codeC = codeC;
        this.description = description;
    }

    public Participation(int codeP, int codeC, String description, int codeU) {
        this.codeP = codeP;
        this.codeU = codeU;
        this.codeC = codeC;
        this.description = description;
    }

    //pour retourner une compétion avec son codeC
    public Competition getCompetition() {
        try {
            // Remplacez le corps de cette méthode avec votre logique effective pour récupérer la Competition
            // associée au codeC en utilisant votre service CompetitionService
            CompetitionService competitionService = new CompetitionService();
            return competitionService.recupererParId(codeC);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception de manière appropriée
            return null;
        }
    }
    public Utilisateur getUtilisateur() {
        try {
            // Remplacez le corps de cette méthode avec votre logique effective pour récupérer l'Utilisateur
            // associé au codeU en utilisant votre service UtilisateurService
            UtilisateurService utilisateurService = new UtilisateurService();
            return utilisateurService.recupererParId(codeU);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception de manière appropriée
            return null;
        }
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
