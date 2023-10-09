package entity;

import java.util.ArrayList;
import java.util.Objects;

public class Etudiant {
    private int idEtudiant;
    private String nomEtudiant;
    private String prenomEtudiant;
    private Formation formation;
    private ArrayList<Projet> projetsRealises;

    public Etudiant(int idEtudiant, String nomEtudiant, String prenomEtudiant, Formation formation,
                    ArrayList<Projet> projetsRealises) {
        super();
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.formation = formation;
        this.projetsRealises = projetsRealises;
    }

    public int getidEtudiant() {
        return this.idEtudiant;
    }

    public void setidEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNomEtudiant() {
        return this.nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return this.prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public ArrayList<Projet> getProjetsRealises() {
        return projetsRealises;
    }

    public void setProjetsRealises(ArrayList<Projet> projetsRealises) {
        this.projetsRealises = projetsRealises;
    }

    @Override
    public String toString() {
        return "Etudiant [idEtudiant=" + idEtudiant + ", nomEtudiant=" + nomEtudiant + ", prenomEtudiant="
                + prenomEtudiant + ", formation=" + formation + ", projetsRealises=" + projetsRealises + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtudiant);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Etudiant other = (Etudiant) obj;
        return idEtudiant == other.idEtudiant;
    }



}




