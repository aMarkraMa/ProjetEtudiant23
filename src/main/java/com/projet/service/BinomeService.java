package com.projet.service;

import com.projet.entity.Binome;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BinomeService {

	List<Binome> getBinomesByIdProjet(Integer idProjet);
	
	Binome selectByIdBinomeAndIdProjet(Binome binome);
	
	void deleteBinome(Integer idBinome, Integer idProjet, Integer idEtudiant1, Integer idEtudiant2);
	
	List<Binome> selectByCondition(String nomMatiere, String sujet, Integer idProjet);
	
	Binome getBinomeByIdProjetAndIdEtudiant(Integer idEtudiant, Integer idProjet);
	
	void addBinome(Binome binome);
	
	void deleteNoteSoutenance(Integer idEtudiant, Integer idProjet);
	void insertNoteSoutenance(Integer idProjet, Integer idEtudiant, Double noteSoutenance);
	
	void updateBinome(Binome binome);
	
	Integer getNbBinomeByIdEtudiant(Integer idEtudiant);
}
