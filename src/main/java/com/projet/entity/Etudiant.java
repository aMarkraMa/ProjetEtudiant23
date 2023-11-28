package com.projet.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Etudiant {
	private Integer idEtudiant;
	private String nomEtudiant;
	private String prenomEtudiant;
	private Formation formation;
	
	
	// Constructor
	public Etudiant() {
	}
	
	public Etudiant(Integer idEtudiant, String nomEtudiant, String prenomEtudiant, Formation formation) {
		this.idEtudiant = idEtudiant;
		this.nomEtudiant = nomEtudiant;
		this.prenomEtudiant = prenomEtudiant;
		this.formation = formation;
	}
	
	// getter et setter
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
	
	public Formation getFormation() {
		return formation;
	}
	
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	
	// toString
	@Override
	public String toString() {
		return "Etudiant{" +
				"idEtudiant=" + idEtudiant +
				", nomEtudiant='" + nomEtudiant + '\'' +
				", prenomEtudiant='" + prenomEtudiant + '\'' +
				", formation=" + formation +
				'}';
	}
	
	// hashCode et equals
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