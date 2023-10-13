package com.projet.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Binome {
    private int idBinome;
    private Projet projet;
    private ArrayList<Etudiant> etudiants;
    private LocalDate dateReelleRemise;

    public Binome(int idBinome, Projet projet, Etudiant etudiant1, Etudiant etudiant2) {
        super();
        this.idBinome = idBinome;
        this.projet = projet;
        this.etudiants = new ArrayList<>();
        etudiants.add(etudiant1);
        etudiants.add(etudiant2);
        this.dateReelleRemise = null;
    }

    public int getidBinome() {
        return idBinome;
    }

    public void setidBinome(int idBinome) {
        this.idBinome = idBinome;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public LocalDate getDateReelleRemise() {
        return dateReelleRemise;
    }

    public void setDateReelleRemise(LocalDate dateReelleRemise) {
        this.dateReelleRemise = dateReelleRemise;
    }

    @Override
    public String toString() {
        return "Binome [idBinome=" + idBinome + ", projet=" + projet + ", etudiants=" + etudiants + ", dateReelleRemise=" + dateReelleRemise + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBinome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Binome other = (Binome) obj;
        return idBinome == other.idBinome;
    }
}
