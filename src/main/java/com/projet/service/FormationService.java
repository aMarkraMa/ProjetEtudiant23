package com.projet.service;

import com.projet.entity.Formation;

import java.util.List;


public interface FormationService {
	
	Formation getFormationById(Formation formation);
	
	void deleteById(Integer idFormation);
	
	List<Formation> selectAll();
	
	List<Formation> selectByCondition(Formation formation);
	
	List<String> getNomsFormation();
	
	List<String> getPromotions();
	
	void addFormation(Formation formation);
	
	
	void updateFormation(Formation formation);
}
