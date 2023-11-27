package com.projet.service;

import com.projet.entity.Note;

import java.util.List;

public interface NoteService {
	List<Note> selectByCondition(Integer idProjet, Integer idEtudiantt);
	
	void updateNoteSoutenance(Integer idProjet, Integer idEtudiant, Double noteSoutenance);
	
	Note getNotesByIdProjetAndIdEtudiant(Integer idProjet, Integer idEtudiant);
}
