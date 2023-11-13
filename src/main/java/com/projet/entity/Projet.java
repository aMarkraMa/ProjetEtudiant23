package com.projet.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Projet {
    private Integer idProjet;
    private String nomMatiere;
    private String sujet;
    private LocalDate datePrevueRemise;
    private Integer pourcentageRapport;
    private Integer pourcentageSoutenance;

    public Projet() {
    }
    
    public Projet(Integer idProjet, String nomMatiere, String sujet, LocalDate datePrevueRemise, Integer pourcentageRapport, Integer pourcentageSoutenance) {
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.datePrevueRemise = datePrevueRemise;
        this.pourcentageRapport = pourcentageRapport;
        this.pourcentageSoutenance = pourcentageSoutenance;
    }
    
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
    
    public Integer getPourcentageRapport() {
        return pourcentageRapport;
    }
    
    public void setPourcentageRapport(Integer pourcentageRapport) {
        this.pourcentageRapport = pourcentageRapport;
    }
    
    public Integer getPourcentageSoutenance() {
        return pourcentageSoutenance;
    }
    
    public void setPourcentageSoutenance(Integer pourcentageSoutenance) {
        this.pourcentageSoutenance = pourcentageSoutenance;
    }
    
    @Override
    public String toString() {
        return "Projet{" +
                "idProjet=" + idProjet +
                ", nomMatiere='" + nomMatiere + '\'' +
                ", sujet='" + sujet + '\'' +
                ", datePrevueRemise=" + datePrevueRemise +
                '}';
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
