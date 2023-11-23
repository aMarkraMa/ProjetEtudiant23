package com.projet.entity;

import java.util.Objects;

public class Note {
    
    
    private Projet projet;
    private Etudiant etudiant;
    private Double noteRapport;
    private Double noteSoutenance;

    public Note() {
        this.projet = new Projet();
        this.etudiant = new Etudiant();
    }
    
    public Note(Projet projet, Etudiant etudiant, Double noteRapport, Double noteSoutenance) {
        this.projet = projet;
        this.etudiant = etudiant;
        this.noteRapport = noteRapport;
        this.noteSoutenance = noteSoutenance;
    }
    public Double getNoteFinale(){
        if(noteRapport < 0 && noteSoutenance >= 0){
            return noteSoutenance;
        } else if (noteRapport >= 0 && noteSoutenance < 0) {
            return noteRapport;
        } else if (noteRapport < 0 && noteSoutenance < 0) {
            return noteRapport;
        } else {
          return calculerNote(noteRapport,noteSoutenance, projet.getPourcentageSoutenance());
        }
    }

    public Double calculerNote(Double noteRapport, Double noteSoutenance, Integer tauxSoutenance){
        double taux = tauxSoutenance / 100;
        return noteRapport * (1 - taux) + noteSoutenance * taux;
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

    public String getNomMatiere(){
        return projet.getNomMatiere();
    }
    public void setNomMatiere(String nomMatiere){
        this.projet.setNomMatiere(nomMatiere);
    }

    public String getSujet() {
        return projet.getSujet();
    }

    public void setSujet(String sujet) {
        this.projet.setSujet(sujet);
    }
//    public String getNomEtudiant() {
//        return this.etudiant.getNomEtudiant();
//    }
//
//    public void setNomEtudiant(String nomEtudiant) {
//        this.etudiant.setNomEtudiant(nomEtudiant);
//    }
//
//    public String getPrenomEtudiant() {
//        return this.etudiant.getPrenomEtudiant();
//    }
//
//    public void setPrenomEtudiant(String prenomEtudiant) {
//        this.etudiant.setPrenomEtudiant(prenomEtudiant);
//    }

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
    

