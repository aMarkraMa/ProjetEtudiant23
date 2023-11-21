package com.projet.entity;

import java.util.Objects;

public class Note {
    
    
    private Projet projet;
    private Etudiant etudiant;
    
    private Double noteRapport;
    private Double noteSoutenance;
    
    public Note() {
    }
    
    public Note(Projet projet, Etudiant etudiant, Double noteRapport, Double noteSoutenance) {
        this.projet = projet;
        this.etudiant = etudiant;
        this.noteRapport = noteRapport;
        this.noteSoutenance = noteSoutenance;
    }
    
    public Double getNoteRapport() {
        return noteRapport;
    }
    
    public void setNoteRapport(Double noteRapport) {
        this.noteRapport = noteRapport;
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
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
                "projet=" + projet +
                ", etudiant=" + etudiant +
                ", noteRapport=" + noteRapport +
                ", noteSoutenance=" + noteSoutenance +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(getProjet(), note.getProjet()) && Objects.equals(getEtudiant(), note.getEtudiant());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getProjet(), getEtudiant());
    }
}
    

