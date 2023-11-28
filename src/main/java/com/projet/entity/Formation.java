package com.projet.entity;

import java.util.Objects;

public class Formation {
	private Integer idFormation;
	private String nomFormation;
	private String promotion;
	
	// Constructor
	public Formation() {
	}
	
	public Formation(Integer idFormation, String nomFormation, String promotion) {
		this.idFormation = idFormation;
		this.nomFormation = nomFormation;
		this.promotion = promotion;
	}
	
	// getter and setter
	public Integer getIdFormation() {
		return idFormation;
	}
	
	public void setIdFormation(Integer idFormation) {
		this.idFormation = idFormation;
	}
	
	public String getNomFormation() {
		return nomFormation;
	}
	
	public void setNomFormation(String nomFormation) {
		this.nomFormation = nomFormation;
	}
	
	public String getPromotion() {
		return promotion;
	}
	
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	
	// toString
	@Override
	public String toString() {
		return "Formation{" +
				"idFormation=" + idFormation +
				", nomFormation='" + nomFormation + '\'' +
				", promotion='" + promotion + '\'' +
				'}';
	}
	
	// equals et hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Formation)) return false;
		Formation formation = (Formation) o;
		return Objects.equals(getIdFormation(), formation.getIdFormation());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getIdFormation());
	}
}
