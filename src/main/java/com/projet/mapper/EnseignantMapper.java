package com.projet.mapper;

import com.projet.entity.Enseigant;

public interface EnseignantMapper {
	void insertEnseignant(Enseigant enseigant);
	
	Enseigant getEnseignant(Enseigant enseigant);
}
