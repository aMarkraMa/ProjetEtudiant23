package com.projet.entity;

import java.time.LocalDate;

public class Projet {
    private Integer idProjet;
    private String nomMatiere;
    private String sujet;
    private LocalDate datePrevueRemise;
    private Integer pourcentageSoutenance;

    //Constructor
    public Projet() {
    }
    
    public Projet(Integer idProjet, String nomMatiere, String sujet, LocalDate datePrevueRemise, Integer pourcentageSoutenance) {
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.datePrevueRemise = datePrevueRemise;
        this.pourcentageSoutenance = pourcentageSoutenance;
    }
    
    //getter et setter
    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
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

    public Integer getPourcentageSoutenance() {
        return pourcentageSoutenance;
    }
    
    public void setPourcentageSoutenance(Integer pourcentageSoutenance) {
        this.pourcentageSoutenance = pourcentageSoutenance;
    }

    //toString
    @Override
    public String toString() {
        return "Projet{" +
                "idProjet=" + idProjet +
                ", nomMatiere='" + nomMatiere + '\'' +
                ", sujet='" + sujet + '\'' +
                ", datePrevueRemise=" + datePrevueRemise +
                ", pourcentageSoutenance=" + pourcentageSoutenance +
                '}';
    }

    //equals
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
