package com.projet.entity;

import java.util.Objects;

public class Note {
    private Binome binome;
    private Etudiant etudiant;
    private Double noteSoutenance;
    
    public Note() {
    }
    
    public Note(Binome binome, Etudiant etudiant, Double noteSoutenance) {
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

    public Integer getIdProjet(){
        return binome.getIdProjet();
    }
    public void setIdProjet(Integer idProjet){
        binome.setIdProjet(idProjet);
    }

    public Integer getIdBinome(){
        return binome.getIdBinome();
    }

    public void setIdBinome(Integer idBinome){
        binome.setIdBinome(idBinome);
    }

    public Projet getProjet(){
        return binome.getProjet();
    }
    public void setProjet(Projet projet){
        binome.setProjet(projet);
    }
    public Integer getIdEtudiant(){
        return etudiant.getIdEtudiant();
    }
    public void setIdEtudiant(Integer idEtudiant){
        etudiant.setIdEtudiant(idEtudiant);
    }

    public String getNomMatiere(){
        return binome.getProjet().getNomMatiere();
    }
    public void setNomMatiere(String nomMatiere){
        binome.getProjet().setNomMatiere(nomMatiere);
    }
    public String getSujet(){
        return binome.getProjet().getSujet();
    }
    public void setSujet(String sujet){
        binome.getProjet().setSujet(sujet);
    }
    public String getNomEtudiant(){
        return etudiant.getNomEtudiant();
    }
    public void setNomEtudiant(String nomEtudiant){
        etudiant.setNomEtudiant(nomEtudiant);
    }
    public String getPrenomEtudiant(){
        return etudiant.getPrenomEtudiant();
    }
    public void setPrenomEtudiant(String prenomEtudiant){
        etudiant.setPrenomEtudiant(prenomEtudiant);
    }
    public Double getNoteRapport(){
        return binome.getNoteRapport();
    }
    public void setNoteRapport(Double noteRapport){
        binome.setNoteRapport(noteRapport);
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
    

