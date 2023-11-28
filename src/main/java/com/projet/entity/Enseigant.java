package com.projet.entity;

import java.util.Objects;

public class Enseigant {
	
	private Integer idEnseignant;
	
	private Integer numeroEnseignant;
	
	private String motDePasseEnseignant;
	
	private String emailEnseignant;
	
	private String question;
	
	private String reponse;
	
	// constructor
	public Enseigant() {
	}
	
	public Enseigant(Integer idEnseignant, Integer numeroEnseignant, String motDePasseEnseignant, String emailEnseignant, String question, String reponse) {
		this.idEnseignant = idEnseignant;
		this.numeroEnseignant = numeroEnseignant;
		this.motDePasseEnseignant = motDePasseEnseignant;
		this.emailEnseignant = emailEnseignant;
		this.question = question;
		this.reponse = reponse;
	}
	
	// getter et setter
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
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getReponse() {
		return reponse;
	}
	
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	
	// toString
	@Override
	public String toString() {
		return "Enseigant{" +
				"idEnseignant=" + idEnseignant +
				", numeroEnseignant=" + numeroEnseignant +
				", motDePasseEnseignant='" + motDePasseEnseignant + '\'' +
				", emailEnseignant='" + emailEnseignant + '\'' +
				", question='" + question + '\'' +
				", reponse='" + reponse + '\'' +
				'}';
	}
	
	// equals et hashCode
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

