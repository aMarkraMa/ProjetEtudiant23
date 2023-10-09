package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Binome {
    private int numBinome;
    private Projet projet;
    private ArrayList<Etudiant> etudiants;
    private double noteRapport;
    private Map<Etudiant, Double> notesSoutenance;
    private LocalDate dateReelleRemise;

    public Binome(int numBinome, Projet projet, Etudiant etudiant1, Etudiant etudiant2, LocalDate dateReelleRemise) {
        super();
        this.numBinome = numBinome;
        this.projet = projet;
        this.etudiants = new ArrayList<>();
        etudiants.add(etudiant1);
        etudiants.add(etudiant2);
        this.noteRapport = 0.0;
        this.notesSoutenance = new HashMap<>();
        notesSoutenance.put(etudiant1, 0.0);
        notesSoutenance.put(etudiant2, 0.0);
        this.dateReelleRemise = null;
    }

    public int getNumBinome() {
        return numBinome;
    }

    public void setNumBinome(int numBinome) {
        this.numBinome = numBinome;
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

    public double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(double noteRapport) {
        this.noteRapport = noteRapport;
    }

    public Map<Etudiant, Double> getNotesSoutenance() {
        return notesSoutenance;
    }

    public void setNotesSoutenance(Map<Etudiant, Double> notesSoutenance) {
        this.notesSoutenance = notesSoutenance;
    }

    public LocalDate getDateReelleRemise() {
        return dateReelleRemise;
    }

    public void setDateReelleRemise(LocalDate dateReelleRemise) {
        this.dateReelleRemise = dateReelleRemise;
    }

    @Override
    public String toString() {
        return "Binome [numBinome=" + numBinome + ", projet=" + projet + ", etudiants=" + etudiants + ", noteRapport="
                + noteRapport + ", notesSoutenance=" + notesSoutenance + ", dateReelleRemise=" + dateReelleRemise + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(numBinome);
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
        return numBinome == other.numBinome;
    }
}
