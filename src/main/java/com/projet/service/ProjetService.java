package com.projet.service;

import com.projet.entity.Projet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjetService {
	
	List<Projet> selectByCondition(Projet projet);
	
	void updateProjet(Projet projet);
	
	void deleteById(Integer idProjet);
	
	List<Projet> selectAll();
	
	List<Integer> getIdsProjet();
	
	List<Integer> getIdsProjetByCondition(String nomMatiere, String sujet);
	
	void addProjet(Projet projet);
}
