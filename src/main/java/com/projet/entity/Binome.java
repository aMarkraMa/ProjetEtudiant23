package com.projet.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Binome {
    private Integer idBinome;
    private Integer idProjet;
    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Date dateReelleRemise;

    public Binome() {
    }

    public Binome(Integer idBinome, Projet projet, Etudiant etudiant1, Etudiant etudiant2, Date dateReelleRemise) {
        this.idBinome = idBinome;
        this.idProjet = projet.getIdProjet();
        this.etudiant1 = etudiant1;
        this.etudiant2 = etudiant2;
        this.dateReelleRemise = dateReelleRemise;
    }

    public Binome(Integer idBinome, Projet projet, Etudiant etudiant, Date dateReelleRemise) {
        this.idBinome = idBinome;
        this.idProjet = projet.getIdProjet();
        this.etudiant1 = etudiant;
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

    public Etudiant getEtudiant1() {
        return etudiant1;
    }

    public void setEtudiant1(Etudiant etudiant1) {
        this.etudiant1 = etudiant1;
    }

    public Etudiant getEtudiant2() {
        return etudiant2;
    }

    public void setEtudiant2(Etudiant etudiant2) {
        this.etudiant2 = etudiant2;
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
        if (o == null || getClass() != o.getClass()) return false;
        Binome binome = (Binome) o;
        return Objects.equals(idBinome, binome.idBinome) && Objects.equals(idProjet, binome.idProjet) && Objects.equals(etudiants, binome.etudiants) && Objects.equals(dateReelleRemise, binome.dateReelleRemise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBinome, idProjet, etudiants, dateReelleRemise);
    }
}
