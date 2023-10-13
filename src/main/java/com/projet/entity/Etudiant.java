package com.projet.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Etudiant {
    private Integer idEtudiant;
    private String nomEtudiant;
    private String prenomEtudiant;
    private Integer idFormation;
    private ArrayList<Projet> projetsRealises;

    public Etudiant() {
    }

    public Etudiant(Integer idEtudiant, String nomEtudiant, String prenomEtudiant, Formation formation,
                    ArrayList<Projet> projetsRealises) {
        super();
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.idFormation = formation.getidFormation();
        this.projetsRealises = projetsRealises;
    }

    public Integer getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Integer idEtudiant) {
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

    public Integer getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Integer idFormation) {
        this.idFormation = idFormation;
    }

    public ArrayList<Projet> getProjetsRealises() {
        return projetsRealises;
    }

    public void setProjetsRealises(ArrayList<Projet> projetsRealises) {
        this.projetsRealises = projetsRealises;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "idEtudiant=" + idEtudiant +
                ", nomEtudiant='" + nomEtudiant + '\'' +
                ", prenomEtudiant='" + prenomEtudiant + '\'' +
                ", idFormation=" + idFormation +
                ", projetsRealises=" + projetsRealises +
                '}';
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




