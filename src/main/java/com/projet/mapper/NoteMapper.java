package com.projet.mapper;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Note;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface NoteMapper {
    
    List<Note> selectAll();
    int insertNote(Note note);
    int deleteNote(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant);
    int updateNote(Note note);
    List<Note> selectByCondition(Note note);
}