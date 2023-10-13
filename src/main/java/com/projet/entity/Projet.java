package com.projet.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Projet {
    private Integer idProjet;
    private String nomMatiere;
    private String sujet;
    private Date datePrevueRemise;

    public Projet() {
    }

    public Projet(Projet projet, String nomMatiere, String sujet, Date datePrevueRemise) {
        this.idProjet = projet.getIdProjet();
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.datePrevueRemise = datePrevueRemise;
    }

    public int getIdProjet() {
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

    public Date getDatePrevueRemise() {
        return datePrevueRemise;
    }

    public void setDatePrevueRemise(Date datePrevueRemise) {
        this.datePrevueRemise = datePrevueRemise;
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
