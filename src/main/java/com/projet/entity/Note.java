package com.projet.entity;

import java.util.Objects;

public class Note {
    private Binome binome;
    private Etudiant etudiant;
    private Double noteSoutenance;
    
    public Note() {
    }
    
    public Note(Binome binome, Etudiant etudiant, Double noteSoutenance, Projet projet) {
        this.binome = binome;
        this.etudiant = etudiant;
        this.noteSoutenance = noteSoutenance;
    }

    public Binome getBinome() {
        return binome;
    }

    public void setBinome(Binome binome) {
        this.binome = binome;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(Double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }

    @Override
    public String toString() {
        return "Note{" +
                "binome=" + binome +
                ", etudiant=" + etudiant +
                ", noteSoutenance=" + noteSoutenance +
                '}';
    }



}
    

