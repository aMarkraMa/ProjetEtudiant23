package com.projet.mapper;

import com.projet.entity.Enseigant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnseignantMapper {
	void insertEnseignant(Enseigant enseigant);
	
	Enseigant getEnseignant(Enseigant enseigant);
	
	List<Integer> getNumerosEnseignant();
	
	List<String> getQuestions();
	
	String getQuestionByNumEnseignant(@Param("numEnseignant") Integer numEnseignant);
	
	String getReponseByNumeroEnseignant(@Param("numEnseignant") Integer numEnseignant);
	
	void updateMotDePasseByNumeroEnseignant(@Param("numEnseignant") Integer numEnseignant, @Param("motDePasse") String motDePasse);
	
	String getEmailEnseignantByNumeroEnseignant(@Param("numeroEnseignant") Integer numeroEnseignant);
	
}
