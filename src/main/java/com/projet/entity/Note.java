package com.projet.entity;

import java.util.Objects;

public class Note {
    private Integer idBinome;
    private double noteRapport;
    private Integer idEtudiant;
    private double noteSoutenance;
    private Integer idProjet;

    public Note() {
    }

    public Note(Binome binome, double noteRapport, Etudiant etudiant, double noteSoutenance, Projet projet) {
        this.idBinome = binome.getIdBinome();
        this.noteRapport = noteRapport;
        this.idEtudiant = etudiant.getIdEtudiant();
        this.noteSoutenance = noteSoutenance;
        this.idProjet = projet.getIdProjet();
    }

    public Integer getIdBinome() {
        return idBinome;
    }

    public void setIdBinome(Integer idBinome) {
        this.idBinome = idBinome;
    }

    public double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(double noteRapport) {
        this.noteRapport = noteRapport;
    }

    public Integer getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Integer idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return Objects.equals(getIdBinome(), note.getIdBinome()) && Objects.equals(getIdEtudiant(), note.getIdEtudiant()) && Objects.equals(getIdProjet(), note.getIdProjet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdBinome(), getIdEtudiant(), getIdProjet());
    }
}
