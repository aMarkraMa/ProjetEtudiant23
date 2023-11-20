package com.projet.entity;

import org.apache.ibatis.annotations.One;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Binome {
	
	private Integer idBinome;
	private Projet projet;
	private LocalDate dateReelleRemise;
	private Double noteRapport;
	private List<Etudiant> etudiants;
	
	public Binome() {
	}
	
	public Binome(Integer idBinome, Projet projet, LocalDate dateReelleRemise, Double noteRapport) {
		this.idBinome = idBinome;
		this.projet = projet;
		this.dateReelleRemise = dateReelleRemise;
		this.noteRapport = noteRapport;
		this.etudiants = new ArrayList<>();
	}
	
	public Integer getIdBinome() {
		return idBinome;
	}
	
	public void setIdBinome(Integer idBinome) {
		this.idBinome = idBinome;
	}
	
	public Projet getProjet() {
		return projet;
	}
	
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	
	public List<Etudiant> getEtudiants() {
		return etudiants;
	}
	
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
	
	public LocalDate getDateReelleRemise() {
		return dateReelleRemise;
	}
	
	public void setDateReelleRemise(LocalDate dateReelleRemise) {
		this.dateReelleRemise = dateReelleRemise;
	}
	
	public Double getNoteRapport() {
		return noteRapport;
	}
	
	public void setNoteRapport(Double noteRapport) {
		this.noteRapport = noteRapport;
	}

	public Integer getIdProjet(){
		return projet.getIdProjet();
	}

	public void setIdProjet(Integer idProjet){
		this.projet.setIdProjet(idProjet);
	}
	@Override
	public String toString() {
		return "Binome{" +
				"idBinome=" + idBinome +
				", projet=" + projet +
				", dateReelleRemise=" + dateReelleRemise +
				", etudiants=" + etudiants +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Binome)) return false;
		Binome binome = (Binome) o;
		return Objects.equals(getIdBinome(), binome.getIdBinome()) && Objects.equals(getProjet(), binome.getProjet());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getIdBinome(), getProjet());
	}
}
