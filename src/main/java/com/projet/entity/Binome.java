package com.projet.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Binome {
    private Integer idBinome;
    private Integer idProjet;
    private ArrayList<Etudiant> etudiants;
    private Date dateReelleRemise;

    public Binome() {
    }

    public Binome(Integer idBinome, Projet projet, ArrayList<Etudiant> etudiants, Date dateReelleRemise) {
        this.idBinome = idBinome;
        this.idProjet = projet.getIdProjet();
        this.etudiants = etudiants;
        this.dateReelleRemise = dateReelleRemise;
    }

    public Integer getIdBinome() {
        return idBinome;
    }

    public void setIdBinome(Integer idBinome) {
        this.idBinome = idBinome;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Date getDateReelleRemise() {
        return dateReelleRemise;
    }

    public void setDateReelleRemise(Date dateReelleRemise) {
        this.dateReelleRemise = dateReelleRemise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Binome binome)) return false;
        return Objects.equals(getIdBinome(), binome.getIdBinome()) && Objects.equals(getIdProjet(), binome.getIdProjet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdBinome(), getIdProjet());
    }
}
