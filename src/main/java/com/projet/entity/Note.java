package com.projet.entity;

import java.util.Objects;

public class Note {
    private int idNote;
    private Binome binome;
    private double noteRapport;
    private Etudiant etudiant;
    private double noteSoutenance;

    public Note(int idNote, Binome binome, double noteRapport, Etudiant etudiant, double noteSoutenance) {
        this.idNote = idNote;
        this.binome = binome;
        this.noteRapport = noteRapport;
        this.etudiant = etudiant;
        this.noteSoutenance = noteSoutenance;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public Binome getBinome() {
        return binome;
    }

    public void setBinome(Binome binome) {
        this.binome = binome;
    }

    public double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(double noteRapport) {
        this.noteRapport = noteRapport;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }

    public int getIdNote() {
        return idNote;
    }

    @Override
    public String toString() {
        return "Note{" +
                "idNote=" + idNote +
                ", binome=" + binome +
                ", noteRapport=" + noteRapport +
                ", etudiant=" + etudiant +
                ", noteSoutenance=" + noteSoutenance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return idNote == note.idNote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNote);
    }
}
