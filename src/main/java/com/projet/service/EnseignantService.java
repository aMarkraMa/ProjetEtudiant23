package com.projet.service;

import com.projet.entity.Enseigant;

import java.util.List;

public interface EnseignantService {
	Enseigant getEnseignant(Enseigant enseigant);
	
	List<String> getQuestions();
	
	List<Integer> getNumerosEnseignant();
	
	void insertEnseignant(Enseigant enseigant);
	
	String getQuestionByNumEnseignant(Integer numEnseignant);
	
	String getReponseByNumeroEnseignant(Integer numEnseignant);
	
	void updateMotDePasseByNumeroEnseignant(Integer numEnseignant, String newPassword);
	
	String getEmailEnseignantByNumeroEnseignant(Integer numEnseignant);
}
