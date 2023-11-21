package com.projet.mapper;

import com.projet.entity.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper {
    
    List<Note> getNotesByIdProjet(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant);
    int deleteNote(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant);
    int updateNote(Note note);
    List<Note> selectByCondition(Note note);
    
}