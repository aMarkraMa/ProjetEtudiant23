package com.projet.entity;

import java.util.Objects;

public class Enseigant {
	
	private Integer idEnseignant;
	
	private Integer numeroEnseignant;
	
	private String motDePasseEnseignant;
	
	private String emailEnseignant;
	
	public Enseigant() {
	}
	
	public Enseigant(Integer idEnseignant, Integer numeroEnseignant, String motDePasseEnseignant, String emailEnseignant) {
		this.idEnseignant = idEnseignant;
		this.numeroEnseignant = numeroEnseignant;
		this.motDePasseEnseignant = motDePasseEnseignant;
		this.emailEnseignant = emailEnseignant;
	}
	
	public Integer getIdEnseignant() {
		return idEnseignant;
	}
	
	public void setIdEnseignant(Integer idEnseignant) {
		this.idEnseignant = idEnseignant;
	}
	
	public Integer getNumeroEnseignant() {
		return numeroEnseignant;
	}
	
	public void setNumeroEnseignant(Integer numeroEnseignant) {
		this.numeroEnseignant = numeroEnseignant;
	}
	
	public String getMotDePasseEnseignant() {
		return motDePasseEnseignant;
	}
	
	public void setMotDePasseEnseignant(String motDePasseEnseignant) {
		this.motDePasseEnseignant = motDePasseEnseignant;
	}
	
	public String getEmailEnseignant() {
		return emailEnseignant;
	}
	
	public void setEmailEnseignant(String emailEnseignant) {
		this.emailEnseignant = emailEnseignant;
	}
	
	@Override
	public String toString() {
		return "Enseigant{" +
				"idEnseignant=" + idEnseignant +
				", numeroEnseignant=" + numeroEnseignant +
				", motDePasseEnseignant='" + motDePasseEnseignant + '\'' +
				", emailEnseignant='" + emailEnseignant + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Enseigant)) return false;
		Enseigant enseigant = (Enseigant) o;
		return Objects.equals(getIdEnseignant(), enseigant.getIdEnseignant());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getIdEnseignant());
	}
}

