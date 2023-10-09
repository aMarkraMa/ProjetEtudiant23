package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Projet {
    private int idProjet;
    private String nomMatiere;
    private String sujet;
    private LocalDate datePrevueRemise;

    public Projet(int idProjet, String nomMatiere, String sujet, LocalDate datePrevueRemise) {
        super();
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.datePrevueRemise = datePrevueRemise;
    }

    public int getidProjet() {
        return idProjet;
    }

    public void setidProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public LocalDate getDatePrevueRemise() {
        return datePrevueRemise;
    }

    public void setDatePrevueRemise(LocalDate datePrevueRemise) {
        this.datePrevueRemise = datePrevueRemise;
    }

    @Override
    public String toString() {
        return "Projet [idProjet=" + idProjet + ", nomMatiere=" + nomMatiere + ", sujet=" + sujet
                + ", datePrevueRemise=" + datePrevueRemise + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Projet other = (Projet) obj;
        return idProjet == other.idProjet;
    }
}
