package com.projet.service;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;

import java.util.List;


public interface EtudiantService {
	
	Etudiant getEtudiantById(Etudiant etudiant);
	
	List<Etudiant> getEtudiantsByIdFormation(Formation formation);
	
	void deleteById(Integer idEtudiant);
	
	List<Etudiant> selectAll();
	
	List<Etudiant> selectByCondition(Etudiant etudiant);
	
	List<Integer> getIdsEtudiant();
	
	List<Integer> getIdsEtudiantByCondition(String nomEtudiant, String prenomEtudiant);
	
	void addEtudiant(Etudiant etudiant);
	
	
	List<String> getNomsEtudiant();
	
	List<String> getPrenomsEtudiant();
	
	void updateEtudiant(Etudiant etudiant);
}
