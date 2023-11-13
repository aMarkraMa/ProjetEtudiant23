package com.projet.mapper;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Note;

import java.util.ArrayList;
import java.util.List;

public interface NoteMapper {
    
    List<Note> selectAll();
    int insertNote(Note note);
    int deleteNote(Note note);
    int updateNote(Note note);
    ArrayList<Note> selectNote(Note note);
}