package com.projet.mapper;

import com.projet.entity.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper {
	
	Note getNotesByIdProjetAndIdEtudiant(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant);
	
	int deleteNote(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant);
	
	int updateNote(Note note);
	
	int updateNoteSoutenance(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant, @Param("noteSoutenance") Double noteSoutenance);
	
	List<Note> selectByCondition(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant, @Param("nomMatiere") String nomMatiere, @Param("sujet") String sujet, @Param("nomEtudiant") String nomEtudiant, @Param("prenomEtudiant") String prenomEtudiant);
	
}