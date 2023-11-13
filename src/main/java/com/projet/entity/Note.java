package com.projet.entity;

import java.util.Objects;

public class Note {
    private Binome binome;
    private Etudiant etudiant;
    private Double noteSoutenance;
    private Projet projet;
    
    public Note() {
    }
    
    public Note(Binome binome, Etudiant etudiant, Double noteSoutenance, Projet projet) {
        this.binome = binome;
        this.etudiant = etudiant;
        this.noteSoutenance = noteSoutenance;
        this.projet = projet;
    }
    
    public Binome getBinome() {
        return binome;
    }
    
    public void setBinome(Binome binome) {
        this.binome = binome;
    }
    
    
    public Double getNoteSoutenance() {
        return noteSoutenance;
    }
    
    public void setNoteSoutenance(double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }
    
    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(getBinome(), note.getBinome()) && Objects.equals(getEtudiant(), note.getEtudiant()) && Objects.equals(getProjet(), note.getProjet());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getBinome(), getEtudiant(), getProjet());
    }
}
    

